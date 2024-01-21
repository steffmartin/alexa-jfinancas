package br.com.steffanmartins.alexajfinancassync.service

import br.com.steffanmartins.alexajfinancassync.config.AppProperties
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.SaldoDocument
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository.DynamoDBRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity.JFinancasContaEntity
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity.JFinancasTipocontaEntity
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasContaRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasMovcontaRepository
import jakarta.persistence.criteria.Predicate
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.io.IOException

@Service
class SaldoService(
    private val props: AppProperties,
    private val contaRepo: JFinancasContaRepository,
    private val movimentoRepo: JFinancasMovcontaRepository,
    private val alexaRepo: DynamoDBRepository
) {

    suspend fun sync() {

        if (!alexaRepo.deletePartition(SaldoDocument(props.userId))) {
            throw IOException("Houve um erro durante a exclusão de saldos.")
        }

        val saldos = contaRepo.findAll(filtroContas())
            .associateWith { movimentoRepo.somaPorConta(it) }
            .map { (conta, saldo) -> toSaldoDocument(conta, saldo) }

        if (!alexaRepo.saveAll(saldos)) {
            throw IOException("Houve um erro durante a gravação de saldos.")
        }

    }

    private fun filtroContas() = Specification<JFinancasContaEntity> { ctaRoot, _, cb ->
        cb.and(
            *mutableListOf<Predicate>().apply {
                add(cb.equal(ctaRoot.get<Short?>("inativo"), 0))

                if (props.somentePrevFinanceira) add(cb.equal(ctaRoot.get<Short?>("incPrevisao"), 1))

                if (props.somentePgInicial) add(cb.equal(ctaRoot.get<Short?>("mostrar"), 1))

                if (props.somenteCtaMovimentacao) {
                    val tipoContaRoot = ctaRoot.join<JFinancasTipocontaEntity, JFinancasContaEntity>("tipoConta")
                    add(cb.equal(tipoContaRoot.get<Short?>("capitalGiro"), 1))
                }
            }.toTypedArray<Predicate>()
        )
    }

    private fun toSaldoDocument(conta: JFinancasContaEntity, saldo: Double) = runCatching {
        SaldoDocument(
            usuario = props.userId,
            tipoConta = conta.tipoConta?.descricao ?: "Sem Tipo",
            conta = conta.nome ?: "Sem Nome",
            saldo = saldo.let { valor -> if (valor.equals(-0.0)) 0.0 else valor }
        )
    }.getOrElse { throw Error("Houve um erro durante a conversão de saldos.") }

}
