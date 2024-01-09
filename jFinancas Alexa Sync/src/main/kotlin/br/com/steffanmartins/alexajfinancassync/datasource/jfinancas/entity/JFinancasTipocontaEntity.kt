package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "TIPOCONTA")
data class JFinancasTipocontaEntity(
    @Id @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "DESCRICAO")
    var descricao: String? = null
)
