package br.com.steffanmartins.alexajfinancassync.datasource.alexa.document

import java.time.LocalDate

data class PrevisaoDocument(
    val usuario: String,
    val vencimento: LocalDate = LocalDate.now(),
    val tipo: TipoPrevisao = TipoPrevisao.PAGAR,
    val credorDevedor: String = "",
    val categoria: String = "",
    val subCategoria: String = "",
    val descricao: String = "",
    val valor: Double = 0.0
) : DynamoDBDocument {

    init {
        require(!valor.equals(-0.0)) { "A zero value cannot be negative" }
    }

    override fun tableName(): String = "AlexaJFinancasPrevisoes"
    override fun partitionKeyName(): String = "Usuario"
    override fun partitionKeyValue(): String = usuario
    override fun sortKeyName(): String = "VencimentoETipo"
    override fun sortKeyValue(): String = "${vencimento}#${tipo}#${hashCode()}"

    enum class TipoPrevisao {
        PAGAR, RECEBER
    }
}

