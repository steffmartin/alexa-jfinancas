package br.com.steffanmartins.alexajfinancassync.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("custom-config.app")
data class AppProperties(
    val userId: String,
    val tipoContaCartao: String,
    val tipoContaSalario: String
)