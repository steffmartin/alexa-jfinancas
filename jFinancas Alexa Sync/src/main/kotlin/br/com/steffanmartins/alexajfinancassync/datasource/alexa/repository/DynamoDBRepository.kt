package br.com.steffanmartins.alexajfinancassync.datasource.alexa.repository

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import aws.sdk.kotlin.services.dynamodb.batchWriteItem
import aws.sdk.kotlin.services.dynamodb.model.WriteRequest
import br.com.steffanmartins.alexajfinancassync.datasource.alexa.document.DynamoDBDocument
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.springframework.stereotype.Repository

@Repository
class DynamoDBRepository(
    private val dynamoDbClient: DynamoDbClient
) {

    suspend fun deleteAllByHashKey(hashKey: String): Boolean {
        //todo query pela hashkey, obter lista (loop ate obter toda lista), mapear lista para deleterequest, fazer batchrequest
        return false
    }

    suspend fun saveAll(documents: Iterable<DynamoDBDocument>): Boolean {
        return withContext(Dispatchers.IO) {
            documents.chunked(25).map {
                dynamoDbClient.batchWriteItem {
                    requestItems = toWriteRequests(it)
                }
            }.all { it.unprocessedItems.isNullOrEmpty() }
        }
    }

    private fun toWriteRequests(chunk: Iterable<DynamoDBDocument>): Map<String, List<WriteRequest>> {
        return chunk.groupBy(DynamoDBDocument::tableName) {
            WriteRequest {
                putRequest {
                    item = it.itemValues()
                }
            }
        }
    }

}
