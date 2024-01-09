package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "TRANSF_PROG")
data class JFinancasTransfProgEntity(
    @Id @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "VENCIMENTO")
    var vencimento: LocalDate? = null,
    @Column(name = "VALOR")
    var valor: Double? = null,
    @Column(name = "HISTORICO")
    var historico: String? = null,
    @Column(name = "STATUS")
    var status: Short? = null,
    @Column(name = "FIMFREQUENCIA")
    var fimFrequencia: Short? = null,
    @ManyToOne @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
    var contaOrigem: JFinancasContaEntity? = null,
    @ManyToOne @JoinColumn(name = "ID_CONTA_TO", referencedColumnName = "ID")
    var contaDestino: JFinancasContaEntity? = null
)
