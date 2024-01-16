package br.com.steffanmartins.alexajfinancassync.datasource.alexa.document

import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import java.time.LocalDate
import java.util.*

//@Target(AnnotationTarget.CLASS)
//annotation class DynamoDBTable(val tableName: String)
//
//@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.FUNCTION)
//annotation class DynamoDBHashKey(val attributeName: String)
//
//@Target(AnnotationTarget.FIELD, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.FUNCTION)
//annotation class DynamoDBRangeKey(val attributeName: String)

interface DynamoDBDocument {
    fun tableName(): String

    fun itemKey(): MutableMap<String, AttributeValue>

    fun itemValues(): MutableMap<String, AttributeValue> = javaClass.declaredFields
        .associate {
            val attributeName = convertFieldNameToAttributeName(it.name)
            val fieldValue = javaClass.getDeclaredMethod("get${attributeName}").invoke(this)
            attributeName to convertFieldValueToAttributeValue(fieldValue)
        }
        .toMutableMap()
        .also { it.putAll(itemKey()) }

    private fun convertFieldNameToAttributeName(name: String) = name.replaceFirstChar { it.titlecase(Locale.getDefault()) }

    private fun convertFieldValueToAttributeValue(value: Any?): AttributeValue = when (value) {
        null -> AttributeValue.Null(true)
        is String -> AttributeValue.S(value)
        is Number -> AttributeValue.N(value.toString())
        is LocalDate -> AttributeValue.S(value.toString())
        is Enum<*> -> AttributeValue.S(value.name)
        else -> throw IllegalArgumentException("Unsupported field type: ${value.javaClass}")
    }
}