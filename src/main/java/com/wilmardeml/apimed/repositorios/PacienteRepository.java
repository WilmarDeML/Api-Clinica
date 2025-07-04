package com.wilmardeml.apimed.repositorios;

import com.wilmardeml.apimed.modelos.entidades.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
