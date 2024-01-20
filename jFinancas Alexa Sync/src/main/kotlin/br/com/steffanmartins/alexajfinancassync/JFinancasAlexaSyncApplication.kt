package br.com.steffanmartins.alexajfinancassync

import br.com.steffanmartins.alexajfinancassync.service.SyncService
import br.com.steffanmartins.alexajfinancassync.view.SystemTrayView
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication
import java.awt.TrayIcon.MessageType.ERROR

fun main(args: Array<String>) {
    runApplication<JFinancasAlexaSyncApplication>(*args)
}

@SpringBootApplication
@ConfigurationPropertiesScan
class JFinancasAlexaSyncApplication(
    private val sysTray: SystemTrayView,
    private val service: SyncService
) : CommandLineRunner {

    override fun run(vararg args: String?) = with(sysTray) {
        notificar("Suas informações financeiras estão sendo sincronizadas com a Alexa agora.")
        runCatching { service.sync() }.onFailure { notificar(it.localizedMessage, ERROR) }
        encerrar()
    }

}
