package br.com.steffanmartins.alexajfinancassync.service

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.SaldoDocument
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity.JFinancasContaEntity
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasContaRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasMovcontaRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class SaldoService(
    private val contaRepo: JFinancasContaRepository,
    private val movimentoRepo: JFinancasMovcontaRepository
) {

    @EventListener(ApplicationReadyEvent::class)
    @Transactional(readOnly = true)
    fun contasAtivas() {
        val contasAtivas = contaRepo.findAll(specBuscarContas())

        val saldos = contasAtivas.map {
            val valor = movimentoRepo.somaPorConta(it).let { soma -> if (soma.equals(-0.0)) 0.0 else soma }
            SaldoDocument("Steffan", it.tipoConta?.descricao ?: "", it.nome ?: "", valor).also { saldo ->
                println("Conta: ${saldo.conta} | Tipo: ${it.tipoConta} | Saldo: ${saldo.saldo}")
                println("tableName: ${saldo.tableName()}")
                println("itemValues: ${saldo.itemValues()}")
            }
        }

    }

    fun specBuscarContas(): Specification<JFinancasContaEntity> = Specification { contaRoot, _, builder ->
        builder.equal(contaRoot.get<Short?>("inativo"), 0)
    }

}
