package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity

import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "CRECEBER")
data class JFinancasCreceberEntity(
    @Id @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "VENCIMENTO")
    var vencimento: LocalDate? = null,
    @Column(name = "VALOR")
    var valor: Double? = null,
    @Column(name = "STATUS")
    var status: Short? = null,
    @Column(name = "FIMFREQUENCIA")
    var fimFrequencia: Short? = null,
    @Column(name = "HISTORICO")
    var historico: String? = null,
    @ManyToOne @JoinColumn(name = "ID_CONTA", referencedColumnName = "ID")
    var conta: JFinancasContaEntity? = null,
    @ManyToOne @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    var cliente: JFinancasClienteEntity? = null,
    @ManyToOne @JoinColumn(name = "ID_PLANCONTA", referencedColumnName = "ID")
    var planoDeContas: JFinancasPlanodecontasEntity? = null,
    @ManyToOne @JoinColumn(name = "ID_TIPOPGTO", referencedColumnName = "ID")
    var tipoPagamento: JFinancasTipopagamentoEntity? = null
)
