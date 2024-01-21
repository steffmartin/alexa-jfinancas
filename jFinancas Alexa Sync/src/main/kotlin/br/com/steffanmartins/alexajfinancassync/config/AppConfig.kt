package br.com.steffanmartins.alexajfinancassync.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("custom-config.app")
data class AppProperties(
    val userId: String,
    val tipoCtaCartao: String,
    val tipoCtaSalario: String,
    val somentePgInicial: Boolean,
    val somentePrevFinanceira: Boolean,
    val somenteCtaMovimentacao: Boolean
)