package com.wilmardeml.apimed.servicios.validaciones;

import com.wilmardeml.apimed.infra.excepciones.ValidacionException;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import com.wilmardeml.apimed.repositorios.ConsultaRepository;
import org.springframework.stereotype.Component;

@Component
public class ValidadorPacienteConOtraConsultaElMismoDia implements ValidadorDeConsultas {

    private final ConsultaRepository repository;

    public ValidadorPacienteConOtraConsultaElMismoDia(ConsultaRepository repository) {
        this.repository = repository;
    }

    public void validar(DatosReservaConsulta datosReserva) {
        var horaInicial = datosReserva.fecha().withHour(7);
        var horaFinal = datosReserva.fecha().withHour(18);
        if (repository.existsByPacienteIdAndFechaBetween(datosReserva.idPaciente(), horaInicial, horaFinal))
            throw new ValidacionException("Paciente ya tiene una consulta reservada para ese día");
    }
}
