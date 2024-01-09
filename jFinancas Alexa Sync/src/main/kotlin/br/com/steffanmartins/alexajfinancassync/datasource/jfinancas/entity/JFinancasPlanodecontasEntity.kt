package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity

import jakarta.persistence.*

@Entity
@Table(name = "PLANODECONTAS")
data class JFinancasPlanodecontasEntity(
    @Id @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "NOME")
    var nome: String? = null,
    @ManyToOne @JoinColumn(name = "ID_OWNER", referencedColumnName = "ID")
    var planoDeContasPai: JFinancasPlanodecontasEntity? = null
)
