package br.com.steffanmartins.alexajfinancassync.service

import aws.sdk.kotlin.services.dynamodb.DynamoDbClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class SyncService(
    private val saldoService: SaldoService,
    private val previsaoService: PrevisaoService,
    private val dynamoDbClient: DynamoDbClient
) {
    fun sync() = runBlocking(Dispatchers.IO) {
        val saldoJob = launch { saldoService.sync() }
        val previsaoJob = launch { previsaoService.sync() }
        saldoJob.join()
        previsaoJob.join()
        dynamoDbClient.close()
    }
}