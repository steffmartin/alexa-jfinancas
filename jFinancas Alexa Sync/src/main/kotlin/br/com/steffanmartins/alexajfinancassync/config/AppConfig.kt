package br.com.steffanmartins.alexajfinancassync.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("config")
data class AppProperties(
    val userId: String
)