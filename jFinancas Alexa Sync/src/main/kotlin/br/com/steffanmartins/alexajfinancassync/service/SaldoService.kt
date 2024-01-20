package br.com.steffanmartins.alexajfinancassync.service

import br.com.steffanmartins.alexajfinancassync.config.AppProperties
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.SaldoDocument
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository.DynamoDBRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity.JFinancasContaEntity
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasContaRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasMovcontaRepository
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

    //    * Opções futuras:
    //    - excluir contas parametrizadas no tipoContaExcluir (ex: Salário, Cartão de Crédito)
    //    - excluir contas parametrizadas no contaExcluir (ex: Empréstimos)
    //    - respeitar o parametro 'excluir todas as contas não mostradas na página inicial'
    //    - respeitar o parametro 'excluir todas as contas não mostradas na previsão financeira'
    private fun filtroContas() = Specification<JFinancasContaEntity> { contaRoot, _, cb ->
        cb.equal(contaRoot.get<Short?>("inativo"), 0)
    }

    private fun toSaldoDocument(conta: JFinancasContaEntity, saldo: Double) = SaldoDocument(
        usuario = props.userId,
        tipoConta = conta.tipoConta?.descricao ?: "Sem Tipo",
        conta = conta.nome ?: "Sem Nome",
        saldo = saldo.let { valor -> if (valor.equals(-0.0)) 0.0 else valor }
    )

}
