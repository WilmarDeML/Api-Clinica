package com.wilmardeml.apimed.repositorios;

import com.wilmardeml.apimed.modelos.entidades.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    boolean existsByPacienteIdAndFechaBetween(Long pacienteId, LocalDateTime horaInicial, LocalDateTime horaFinal);

    boolean existsByMedicoIdAndFecha(Long medicoId, LocalDateTime fecha);
}
