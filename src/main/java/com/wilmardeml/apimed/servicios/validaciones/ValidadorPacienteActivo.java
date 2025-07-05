package com.wilmardeml.apimed.servicios.validaciones;

import com.wilmardeml.apimed.infra.excepciones.ValidacionException;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import com.wilmardeml.apimed.repositorios.PacienteRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteActivo implements ValidadorDeConsultas {

    private final PacienteRepository repository;

    public ValidadorPacienteActivo(PacienteRepository repository) {
        this.repository = repository;
    }

    public void validar(DatosReservaConsulta datosReserva) {

        if (!repository.existsByIdAndActivoTrue(datosReserva.idPaciente()))
            throw new ValidacionException("Paciente inactivo, consulta no puede ser reservada");
    }
}
