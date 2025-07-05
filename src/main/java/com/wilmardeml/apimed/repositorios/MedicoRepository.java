package com.wilmardeml.apimed.repositorios;

import com.wilmardeml.apimed.modelos.entidades.Medico;
import com.wilmardeml.apimed.modelos.enums.Especialidad;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByActivoTrue(Pageable paginacion);

    @Query("""
            SELECT m FROM Medico m
            WHERE m.activo = TRUE AND m.especialidad = :especialidad
            AND m not in(
                SELECT c.medico FROM Consulta c
                WHERE c.fecha = :fecha
            )
            ORDER BY rand()
            LIMIT 1
            """)
    Medico elegirMedicoAleatorioDisponibleEnFecha(Especialidad especialidad, LocalDateTime fecha);

    boolean existsByIdAndActivoTrue(Long id);
}
