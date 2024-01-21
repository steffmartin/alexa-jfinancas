package br.com.steffanmartins.alexajfinancassync.service

import br.com.steffanmartins.alexajfinancassync.config.AppProperties
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.PrevisaoDocument
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.PrevisaoDocument.TipoPrevisao.PAGAR
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.PrevisaoDocument.TipoPrevisao.RECEBER
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository.DynamoDBRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity.*
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasCpagarRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasCreceberRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasTransfProgRepository
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class PrevisaoService(
    private val props: AppProperties,
    private val receberRepo: JFinancasCreceberRepository,
    private val pagarRepo: JFinancasCpagarRepository,
    private val transfRepo: JFinancasTransfProgRepository,
    private val alexaRepo: DynamoDBRepository
) {
    suspend fun sync() {

        if (!alexaRepo.deletePartition(PrevisaoDocument(props.userId))) {
            throw IOException("Houve um erro durante a exclusão de previsões.")
        }

        val previsoes = (aReceber() + aPagar() + cartao() + salario()).map(::toPrevisaoDocument)

        if (!alexaRepo.saveAll(previsoes)) {
            throw IOException("Houve um erro durante a gravação de previsões.")
        }
    }

    private fun aReceber() = receberRepo.findAll(filtroPrevisao())

    private fun aPagar() = pagarRepo.findAll(filtroPrevisao())

    private fun cartao() = transfRepo.findAll(filtroPrevisao(isCartao = true))

    private fun salario() = runCatching {
        val aPagar = pagarRepo.findAll(filtroPrevisao(isSalario = true))
            .groupBy { it.conta to it.vencimento }
            .mapValues {
                it.value.sortedByDescending(JFinancasCpagarEntity::valor).reduce { acc, entity ->
                    acc.apply {
                        valor = valor!! + entity.valor!!
                    }
                }
            }.toMutableMap()

        val aReceberLiquido = receberRepo.findAll(filtroPrevisao(isSalario = true))
            .groupBy { it.conta to it.vencimento }
            .mapValues {
                it.value.sortedByDescending(JFinancasCreceberEntity::valor).reduce { acc, entity ->
                    acc.apply {
                        valor = valor!! + entity.valor!!
                    }
                }
            }.map {
                it.value.apply {
                    val desconto = aPagar.remove(it.key)
                    if (desconto != null) {
                        valor = valor!! - desconto.valor!!
                    }
                }
            }.filter { it.valor!!.compareTo(0.0) != 0 }

        aReceberLiquido + aPagar.values
    }.getOrElse { throw Error("Houve um erro durante o cálculo do salário líquido.") }

    private fun <T> filtroPrevisao(isSalario: Boolean = false, isCartao: Boolean = false) =
        Specification<T> { prevRoot, _, cb ->
            val ctaRoot = prevRoot.join<T, JFinancasContaEntity>(if (isCartao) "contaDestino" else "conta")
            val tpCtaRoot = ctaRoot.join<JFinancasContaEntity, JFinancasTipocontaEntity>("tipoConta")

            cb.and(
                *mutableListOf<Predicate>().apply {
                    add(cb.equal(prevRoot.get<Short?>("status"), 0))

                    add(cb.equal(prevRoot.get<Short?>("fimFrequencia"), 0))

                    if (isCartao) add(cb.equal(tpCtaRoot.get<String?>("descricao"), props.tipoCtaCartao))
                    else if (isSalario) add(cb.equal(tpCtaRoot.get<String?>("descricao"), props.tipoCtaSalario))
                    else add(tpCtaRoot.get<String?>("descricao").`in`(props.tipoCtaSalario, props.tipoCtaCartao).not())

                    if (props.somentePrevFinanceira) add(cb.equal(ctaRoot.get<Short?>("incPrevisao"), 1))

                    if (props.somentePgInicial) add(cb.equal(ctaRoot.get<Short?>("mostrar"), 1))

                    if (props.somenteCtaMovimentacao) add(cb.equal(tpCtaRoot.get<Short?>("capitalGiro"), 1))
                }.toTypedArray<Predicate>()
            )
        }

    private fun toPrevisaoDocument(entity: Any) = runCatching {
        when (entity) {
            is JFinancasCreceberEntity -> PrevisaoDocument(
                usuario = props.userId,
                tipo = RECEBER,
                vencimento = entity.vencimento!!,
                valor = entity.valor!!,
                descricao = entity.historico ?: "Sem descrição",
                credorDevedor = entity.cliente?.nomeFantasia ?: "Diversos",
                categoria = entity.planoDeContas?.planoDeContasPai?.nome ?: "Receitas",
                subCategoria = entity.planoDeContas?.nome ?: "Receitas Diversas"
            )

            is JFinancasCpagarEntity -> PrevisaoDocument(
                usuario = props.userId,
                tipo = PAGAR,
                vencimento = entity.vencimento!!,
                valor = entity.valor!!,
                descricao = entity.historico ?: "Sem descrição",
                credorDevedor = entity.cliente?.nomeFantasia ?: "Diversos",
                categoria = entity.planoDeContas?.planoDeContasPai?.nome ?: "Despesas",
                subCategoria = entity.planoDeContas?.nome ?: "Despesas Diversas"
            )

            is JFinancasTransfProgEntity -> PrevisaoDocument(
                usuario = props.userId,
                tipo = PAGAR,
                vencimento = entity.vencimento!!,
                valor = entity.valor!!,
                descricao = entity.historico ?: "Sem descrição",
                credorDevedor = entity.contaDestino!!.nome ?: "Diversos",
                categoria = "Despesas",
                subCategoria = "Despesas Diversas"
            )

            else -> throw Error()
        }
    }.getOrElse { throw Error("Houve um erro durante a conversão de previsões para a clase ${entity.javaClass}.") }
}

