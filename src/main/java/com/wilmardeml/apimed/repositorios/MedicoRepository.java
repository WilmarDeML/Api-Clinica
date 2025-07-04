package com.wilmardeml.apimed.repositorios;

import com.wilmardeml.apimed.modelos.entidades.Medico;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
    Page<Medico> findAllByActivoTrue(Pageable paginacion);
}
