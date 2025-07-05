package com.wilmardeml.apimed.servicios.validaciones;

import com.wilmardeml.apimed.infra.excepciones.ValidacionException;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Component
public class ValidadorConsultaConAnticipacion implements ValidadorDeConsultas {
    public void validar(DatosReservaConsulta datosReserva) {
        var fechaConsulta = datosReserva.fecha();
        if (Duration.between(LocalDateTime.now(), fechaConsulta).toMinutes() < 30)
            throw new ValidacionException("Horario seleccionado es menor a 30 minutos de anticipación");
    }
}
