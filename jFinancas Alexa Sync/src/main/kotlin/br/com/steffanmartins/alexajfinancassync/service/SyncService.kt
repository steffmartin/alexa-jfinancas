package br.com.steffanmartins.alexajfinancassync.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Service

@Service
class SyncService(
    private val saldoService: SaldoService,
    private val previsaoService: PrevisaoService
) {
    fun sync() = runBlocking(Dispatchers.IO) {
        launch { saldoService.sync() }
        launch { previsaoService.sync() }
    }
}