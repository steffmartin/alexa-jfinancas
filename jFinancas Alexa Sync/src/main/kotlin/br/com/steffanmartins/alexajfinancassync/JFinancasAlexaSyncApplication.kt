package br.com.steffanmartins.alexajfinancassync

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.runApplication

@SpringBootApplication
@ConfigurationPropertiesScan
class JFinancasAlexaSyncApplication

fun main(args: Array<String>) {
    runApplication<JFinancasAlexaSyncApplication>(*args)
}
