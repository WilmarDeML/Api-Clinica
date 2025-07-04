package com.wilmardeml.apimed.controladores;

import com.wilmardeml.apimed.modelos.dtos.*;
import com.wilmardeml.apimed.modelos.entidades.Paciente;
import com.wilmardeml.apimed.repositorios.PacienteRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    private final PacienteRepository repository;

    public PacienteController(PacienteRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetallePaciente> registrar(@RequestBody @Valid DatosRegistroPaciente datosPaciente) {
        var paciente = new Paciente(datosPaciente);
        repository.save(paciente);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(paciente.getId())
                .toUri();

        return ResponseEntity.created(location).body(new DatosDetallePaciente(paciente));
    }
}
