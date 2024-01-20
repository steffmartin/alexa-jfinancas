package br.com.steffanmartins.alexajfinancassync.datasource.alexa.document

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import java.time.LocalDate
import java.util.*

interface DynamoDBDocument {

    fun tableName(): String
    fun partitionKeyName(): String
    fun partitionKeyValue(): Any
    fun sortKeyName(): String
    fun sortKeyValue(): Any

    fun partitionKeyAttribute(): AttributeValue = toAttributeValue(partitionKeyValue())

    fun sortKeyAttribute(): AttributeValue = toAttributeValue(sortKeyValue())

    fun itemValues(): MutableMap<String, AttributeValue> = javaClass.declaredFields
        .associate {
            val attributeName = toAttributeName(it.name)
            val fieldValue = javaClass.getDeclaredMethod("get${attributeName}").invoke(this)
            attributeName to toAttributeValue(fieldValue)
        }
        .toMutableMap()
        .also {
            it[partitionKeyName()] = partitionKeyAttribute()
            it[sortKeyName()] = sortKeyAttribute()
        }

    private fun toAttributeName(name: String) = name.replaceFirstChar { it.titlecase(Locale.getDefault()) }

    private fun toAttributeValue(value: Any?): AttributeValue = when (value) {
        null -> AttributeValue.Null(true)
        is String -> AttributeValue.S(value)
        is Number -> AttributeValue.N(value.toString())
        is LocalDate -> AttributeValue.S(value.toString())
        is Enum<*> -> AttributeValue.S(value.name)
        else -> throw NotImplementedError("Conversão para AttributeValue não implementada para a classe: ${value.javaClass}")
    }
}