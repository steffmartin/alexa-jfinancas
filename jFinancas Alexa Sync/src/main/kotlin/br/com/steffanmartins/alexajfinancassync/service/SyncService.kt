package br.com.steffanmartins.alexajfinancassync.service

import org.springframework.stereotype.Service

@Service
class SyncService(
    private val saldoService: SaldoService,
    private val previsaoService: PrevisaoService
) {
    suspend fun sync() {
        saldoService.sync()
        previsaoService.sync()
    }
}