package br.com.steffanmartins.alexajfinancassync.service

import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity.JFinancasContaEntity
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasContaRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasMovcontaRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service

@Service
class SaldoService(
    val contaRepo: JFinancasContaRepository,
    val movimentoRepo: JFinancasMovcontaRepository
) {

    @EventListener(ApplicationReadyEvent::class)
    fun contasAtivas() {
        val contasAtivas = contaRepo.findAll(specBuscarContas())

        contasAtivas.forEach {
            println("O saldo da conta ${it.nome} é R$ ${movimentoRepo.somaPorConta(it)}")
        }
    }

    fun specBuscarContas(): Specification<JFinancasContaEntity> = Specification { contaRoot, _, builder ->
        builder.equal(contaRoot.get<Short?>("inativo"), 0)
    }

}
