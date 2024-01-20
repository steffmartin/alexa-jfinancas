package br.com.steffanmartins.alexajfinancassync

import br.com.steffanmartins.alexajfinancassync.service.SyncService
import br.com.steffanmartins.alexajfinancassync.view.SystemTrayView
import kotlinx.coroutines.runBlocking
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

fun main(args: Array<String>) {
    runApplication<JFinancasAlexaSyncApplication>(*args)
}

@SpringBootApplication
@ConfigurationPropertiesScan
class JFinancasAlexaSyncApplication(
    private val view: SystemTrayView,
    private val service: SyncService
) : CommandLineRunner {

    override fun run(vararg args: String?) = with(view) {
        runBlocking {
            showInfo("Suas informações financeiras estão sendo sincronizadas com a Alexa agora.")
            runCatching { service.sync() }.onFailure { showError(it.localizedMessage) }
            close()
        }
    }

}
