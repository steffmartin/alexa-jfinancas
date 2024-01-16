package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity

import jakarta.persistence.*

@Entity
@Table(name = "CONTA")
data class JFinancasContaEntity(
    @Id @Column(name = "ID")
    var id: Int? = null,
    @Column(name = "NOME")
    var nome: String? = null,
    @Column(name = "INATIVO")
    var inativo: Short? = null,
    @Column(name = "MOSTRAR_PAGINICIAL")
    var mostrar: Short? = null,
    @Column(name = "INCPREVISAOFIN")
    var incPrevisao: Short? = null,
    @ManyToOne @JoinColumn(name = "ID_TIPOCONTA", referencedColumnName = "ID")
    var tipoConta: JFinancasTipocontaEntity? = null
)
