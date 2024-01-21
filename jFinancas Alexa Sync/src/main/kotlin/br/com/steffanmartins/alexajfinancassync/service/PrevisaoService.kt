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

    //   * Opções futuras:
    //       - excluir contas parametrizadas no tipoContaExcluir (ex: Salário, Cartão de Crédito)
    //       - excluir contas parametrizadas no contaExcluir (ex: Empréstimos)
    //       - excluir contas parametrizadas no categoriaExcluir (ex: Doações)
    //       - respeitar o parametro 'excluir todas as contas não mostradas na página inicial'
    //       - respeitar o parametro 'excluir todas as contas não mostradas na previsão financeira'
    private fun <T> filtroPrevisao(isSalario: Boolean = false, isCartao: Boolean = false) =
        Specification<T> { previsaoRoot, _, cb ->
            val contaRoot = previsaoRoot.join<T, JFinancasContaEntity>(if (isCartao) "contaDestino" else "conta")
            val tipoContaRoot = contaRoot.join<JFinancasContaEntity, JFinancasTipocontaEntity>("tipoConta")

            cb.and(
                cb.equal(previsaoRoot.get<Short?>("status"), 0),
                cb.equal(previsaoRoot.get<Short?>("fimFrequencia"), 0),
                if (isCartao) {
                    cb.equal(tipoContaRoot.get<String?>("descricao"), props.tipoContaCartao)
                } else if (isSalario) {
                    cb.equal(tipoContaRoot.get<String?>("descricao"), props.tipoContaSalario)
                } else {
                    tipoContaRoot.get<String?>("descricao").`in`(props.tipoContaSalario, props.tipoContaCartao).not()
                }
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

