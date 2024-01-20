package br.com.steffanmartins.alexajfinancassync.service

import br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository.DynamoDBRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasContaRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasMovcontaRepository
import org.springframework.stereotype.Service

@Service
class SaldoService(
    private val contaRepo: JFinancasContaRepository,
    private val movimentoRepo: JFinancasMovcontaRepository,
    private val alexaRepo: DynamoDBRepository
) {
    fun sync() {
        TODO(
            """
           Apagar todos os registros da tabela de Saldo no dynamo para o usuário deste pc
           - delete partition
           
           Listar todas as contas ativas do jFinanças, cláusula where:
           - inativo = 0
           
           Buscar o saldo de todas as contas encontradas pelo repositório de movimentos
           
           Converter as entidades jFinancas para entidades Alexa
           - Saldos com valor -0.0 devem ser ajustados para 0.0
           
           Salvar todos os saldos na tabela do dynamo
           
           * Qualquer erro deve lançar uma exceção com mensagem legível, a mesma é exibida no windows 
           
           * Opções futuras:
               - excluir contas parametrizadas no tipoContaExcluir (ex: Salário, Cartão de Crédito)
               - excluir contas parametrizadas no contaExcluir (ex: Empréstimos)
               - respeitar o parametro 'excluir todas as contas não mostradas na página inicial'
               - respeitar o parametro 'excluir todas as contas não mostradas na previsão financeira'
            
        """
        )
    }

}
