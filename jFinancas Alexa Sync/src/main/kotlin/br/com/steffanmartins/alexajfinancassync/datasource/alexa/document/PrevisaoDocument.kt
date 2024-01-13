package br.com.steffanmartins.alexajfinancassync.datasource.alexa.document

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import java.time.LocalDate

data class PrevisaoDocument(
    val usuario: String,
    val vencimento: LocalDate,
    val tipo: TipoPrevisao,
    val conta: String,
    val credorDevedor: String,
    val categoria: String,
    val subCategoria: String,
    val descricao: String = "",
    val valor: Double
) : DynamoDBDocument {

    init {
        require(!valor.equals(-0.0)) { "A zero value cannot be negative" }
    }

    override fun tableName(): String = "AlexaJFinancasPrevisoes"

    override fun itemKey(): MutableMap<String, AttributeValue> = mutableMapOf(
        "Usuario" to AttributeValue.S(usuario),
        "VencimentoETipo" to AttributeValue.S("${vencimento}#${tipo}")
    )
}

enum class TipoPrevisao {
    PAGAR, RECEBER
}
