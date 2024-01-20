package br.com.steffanmartins.alexajfinancassync.service

import br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository.DynamoDBRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasCpagarRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasCreceberRepository
import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository.JFinancasTransfProgRepository
import org.springframework.stereotype.Service

@Service
class PrevisaoService(
    private val receberRepo: JFinancasCreceberRepository,
    private val pagarRepo: JFinancasCpagarRepository,
    private val transfRepo: JFinancasTransfProgRepository,
    private val alexaRepo: DynamoDBRepository
) {
    suspend fun sync() {
//            Ler os parâmetros:
//            - tipoContaCartao: deve ser o tipo de conta onde os cartões são cadastrados
//            - tipoContaSalario: deve ser o tipo de conta onde contas salario sao cadastradas
//
//           Apagar todos os registros da tabela de Previsão no dynamo para o usuário deste pc
//           - delete partition
//
//           Buscar todas as contas a receber no JFinancasCreceberRepository
//           - trazer somente ativas: status = 0
//           - não trazer agrupador de parcela: AND FIMFREQUENCIA = 0
//           - nao trazer cartão (tipoContaCartao), isso é outro filtro: AND (tp.DESCRICAO IS NULL OR tp.DESCRICAO  <> 'Cartão de Crédito')
//           - nao trazer salário (tipoContaSalario), isso é outro filtro: AND c.NOME NOT LIKE '%Salário%'
//
//           Buscar todas as contas a pagar no JFinancasCpagarRepository
//           - trazer somente ativas: status = 0
//           - não trazer agrupador de parcela: AND FIMFREQUENCIA = 0
//           - nao trazer cartão (tipoContaCartao), isso é outro filtro: AND (tp.DESCRICAO IS NULL OR tp.DESCRICAO  <> 'Cartão de Crédito')
//           - nao trazer salário (tipoContaSalario), isso é outro filtro: AND c.NOME NOT LIKE '%Salário%'
//
//           Buscar os valores de cartão de crédito no JFinancasTransfProgRepository
//           - trazer somente ativas: status = 0
//           - não trazer agrupador de parcela: AND FIMFREQUENCIA = 0
//           - trazer somente os cartões (tipoContaCartao): AND C.NOME LIKE %Cartão%
//
//           Buscar os valores do salário no JFinancasCreceberRepository e JFinancasCpagarRepository
//           - trazer somente ativas:"STATUS" = 0
//	       - não trazer agrupador de parcela: AND FIMFREQUENCIA = 0
//	       - trazer somente o salário (tipoContaSalario): AND C1.NOME LIKE '%Salário%'
//
//           * Para o salário deve ser feito uma agregação:
//                - Agregar os valores a pagar e a receber por data e conta
//                - O valor será a soma de todos os positivos e negativos daquela data
//                - Se o valor for positivo, o item 'modelo' será a maior receita
//                - Se o valor for negativo, o item 'modelo' será a maior despesa
//                - No fim, somente uma previsão por data+conta irá persistir
//
//           Converter as entidades jFinancas para entidades Alexa
//           - Previsoes com valor -0.0 devem ser ajustados para 0.0
//
//           Salvar todas as previsões na tabela do dynamo
//
//           * Qualquer erro deve lançar uma exceção com mensagem legível, a mesma é exibida no windows
//
//           * Opções futuras:
//               - excluir contas parametrizadas no tipoContaExcluir (ex: Salário, Cartão de Crédito)
//               - excluir contas parametrizadas no contaExcluir (ex: Empréstimos)
//               - excluir contas parametrizadas no categoriaExcluir (ex: Doações)
//               - respeitar o parametro 'excluir todas as contas não mostradas na página inicial'
//               - respeitar o parametro 'excluir todas as contas não mostradas na previsão financeira'
    }

}
