package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "CLIENTE")
data class JFinancasClienteEntity(
    @Id @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "NOMEFANTASIA")
    var nomeFantasia: String? = null
)
