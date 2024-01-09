package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "MOVCONTA")
data class JFinancasMovcontaEntity(
    @Id @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "TIPO")
    var tipo: Short? = null,
    @Column(name = "VALOR")
    var valor: Double? = null,
    @Column(name = "STATUS")
    var status: Short? = null,
    @Column(name = "DATA")
    var data: LocalDate? = null,
    @ManyToOne @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
    var conta: JFinancasContaEntity? = null
)
