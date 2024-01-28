package br.com.steffanmartins.alexajfinancassync.datasource.alexa.document

data class SaldoDocument(
    val usuario: String,
    val tipoConta: String = "",
    val conta: String = "",
    val saldo: Double = 0.0
) : DynamoDBDocument {

    init {
        require(!saldo.equals(-0.0)) { "A zero value cannot be negative" }
    }

    override fun tableName(): String = "AlexaJFinancasSaldo"
    override fun partitionKeyName(): String = "Usuario"
    override fun partitionKeyValue(): String = usuario
    override fun sortKeyName(): String = "TipoEConta"
    override fun sortKeyValue(): String = "${tipoConta}#${conta}".uppercase()
}
