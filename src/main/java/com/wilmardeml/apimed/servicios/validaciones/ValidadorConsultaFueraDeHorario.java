package com.wilmardeml.apimed.servicios.validaciones;

import com.wilmardeml.apimed.infra.excepciones.ValidacionException;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;

@Component
public class ValidadorConsultaFueraDeHorario implements ValidadorDeConsultas {
    public void validar(DatosReservaConsulta datosReserva) {
        var fechaConsulta = datosReserva.fecha();
        if (fechaConsulta.getDayOfWeek().equals(DayOfWeek.SUNDAY)
                || fechaConsulta.getHour() < 7
                || fechaConsulta.getHour() > 18
        )
            throw new ValidacionException("Horario seleccionado fuera del horario de atención de la clínica.");
    }
}
