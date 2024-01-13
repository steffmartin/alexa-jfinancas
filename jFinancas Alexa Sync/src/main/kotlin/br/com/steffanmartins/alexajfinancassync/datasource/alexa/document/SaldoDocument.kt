package br.com.steffanmartins.alexajfinancassync.datasource.alexa.document

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue

data class SaldoDocument(
    val usuario: String,
    val tipoConta: String,
    val conta: String,
    val saldo: Double = 0.0
) : DynamoDBDocument {

    init {
        require(!saldo.equals(-0.0)) { "A zero value cannot be negative" }
    }

    override fun tableName(): String = "AlexaJFinancasSaldo"

    override fun itemKey(): MutableMap<String, AttributeValue> = mutableMapOf(
        "Usuario" to AttributeValue.S(usuario),
        "TipoEConta" to AttributeValue.S("${tipoConta}#${conta}")
    )
}
