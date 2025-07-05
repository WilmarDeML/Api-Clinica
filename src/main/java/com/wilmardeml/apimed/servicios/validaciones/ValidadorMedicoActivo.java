package com.wilmardeml.apimed.servicios.validaciones;

import com.wilmardeml.apimed.infra.excepciones.ValidacionException;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import com.wilmardeml.apimed.repositorios.MedicoRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoActivo implements ValidadorDeConsultas {

    private final MedicoRepository repository;

    public ValidadorMedicoActivo(MedicoRepository repository) {
        this.repository = repository;
    }

    public void validar(DatosReservaConsulta datosReserva) {

        if (datosReserva.idMedico() == null) return;

        if (!repository.existsByIdAndActivoTrue(datosReserva.idMedico()))
            throw new ValidacionException("Medico inactivo, consulta no puede ser reservada");
    }
}
