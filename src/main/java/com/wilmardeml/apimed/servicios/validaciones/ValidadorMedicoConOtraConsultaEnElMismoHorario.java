package com.wilmardeml.apimed.servicios.validaciones;

import com.wilmardeml.apimed.infra.excepciones.ValidacionException;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import com.wilmardeml.apimed.repositorios.ConsultaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorMedicoConOtraConsultaEnElMismoHorario implements ValidadorDeConsultas {

    private final ConsultaRepository repository;

    public ValidadorMedicoConOtraConsultaEnElMismoHorario(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DatosReservaConsulta datosReserva) {
        if (repository.existsByMedicoIdAndFecha(datosReserva.idMedico(), datosReserva.fecha()))
            throw new ValidacionException("Médico ya tiene una consulta en esa misma fecha y hora");
    }
}
