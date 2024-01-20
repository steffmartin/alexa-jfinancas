package br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.repository

import br.com.steffanmartins.alexajfinancassync.datasource.jfinancas.entity.*
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.Repository
import java.time.LocalDate


interface JFinancasContaRepository : JpaSpecificationExecutor<JFinancasContaEntity>,
    Repository<JFinancasContaEntity, Int>

interface JFinancasCpagarRepository : JpaSpecificationExecutor<JFinancasCpagarEntity>,
    Repository<JFinancasCpagarEntity, Int>

interface JFinancasCreceberRepository : JpaSpecificationExecutor<JFinancasCreceberEntity>,
    Repository<JFinancasCreceberEntity, Int>

interface JFinancasTransfProgRepository : JpaSpecificationExecutor<JFinancasTransfProgEntity>,
    Repository<JFinancasTransfProgEntity, Int>

interface JFinancasMovcontaRepository : JpaSpecificationExecutor<JFinancasMovcontaEntity>,
    Repository<JFinancasMovcontaEntity, Int> {
    @Query(
        """
            SELECT COALESCE(ROUND(SUM(m.valor * (CASE WHEN m.tipo IN (1, 2) THEN -1 ELSE 1 END)), 2),0)
            FROM JFinancasMovcontaEntity m
            WHERE m.data <= :dataLimite
              AND m.status <> 31
              AND m.conta = :contaBuscada
        """
    )
    fun somaPorConta(contaBuscada: JFinancasContaEntity, dataLimite: LocalDate = LocalDate.now()): Double
}

//interface JFinancasClienteRepository : JpaSpecificationExecutor<JFinancasClienteEntity>,
//    Repository<JFinancasClienteEntity, Int>

//interface JFinancasPlanodecontasRepository : JpaSpecificationExecutor<JFinancasPlanodecontasEntity>,
//    Repository<JFinancasPlanodecontasEntity, Int>

//interface JFinancasTipocontaRepository : JpaSpecificationExecutor<JFinancasTipocontaEntity>,
//    Repository<JFinancasTipocontaEntity, Int>