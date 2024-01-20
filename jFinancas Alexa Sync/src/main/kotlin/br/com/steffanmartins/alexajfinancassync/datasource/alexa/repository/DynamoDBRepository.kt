package br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.batchWriteItem
import aws.sdk.kotlin.services.dynamodb.model.AttributeValue
import aws.sdk.kotlin.services.dynamodb.model.WriteRequest
import aws.sdk.kotlin.services.dynamodb.paginators.queryPaginated
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.DynamoDBDocument
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Repository

@Repository
class DynamoDBRepository(
    private val dbClient: DynamoDbClient
) {

    suspend fun deletePartition(example: DynamoDBDocument): Boolean =
        getKeys(example).chunked(25).map {
            dbClient.batchWriteItem {
                requestItems = toDeleteRequests(example.tableName(), it)
            }
        }.all { it.unprocessedItems.isNullOrEmpty() }

    @OptIn(ExperimentalCoroutinesApi::class) //flatMapConcat needs optIn
    private suspend fun getKeys(example: DynamoDBDocument): List<Map<String, AttributeValue>> = with(example) {
        dbClient.queryPaginated {
            tableName = tableName()
            projectionExpression = "${partitionKeyName()}, ${sortKeyName()}"
            keyConditionExpression = "${partitionKeyName()} = :partitionKeyAttribute"
            expressionAttributeValues = mapOf(":partitionKeyAttribute" to partitionKeyAttribute())
        }.flatMapConcat {
            it.items?.asFlow() ?: emptyFlow()
        }.toList()
    }

    private fun toDeleteRequests(tableName: String, keysChunk: Iterable<Map<String, AttributeValue>>):
            Map<String, List<WriteRequest>> = mapOf(tableName to keysChunk.map {
        WriteRequest {
            deleteRequest {
                key = it
            }
        }
    })

    suspend fun saveAll(documents: Iterable<DynamoDBDocument>): Boolean =
        documents.chunked(25).map {
            dbClient.batchWriteItem {
                requestItems = toWriteRequests(it)
            }
        }.all { it.unprocessedItems.isNullOrEmpty() }


    private fun toWriteRequests(chunk: Iterable<DynamoDBDocument>): Map<String, List<WriteRequest>> =
        chunk.groupBy(DynamoDBDocument::tableName) {
            WriteRequest {
                putRequest {
                    item = it.itemValues()
                }
            }
        }


}
