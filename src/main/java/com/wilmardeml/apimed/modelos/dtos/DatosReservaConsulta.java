package com.wilmardeml.apimed.modelos.dtos;

import com.wilmardeml.apimed.modelos.enums.Especialidad;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record DatosReservaConsulta(
        Long idMedico,
        @NotNull Long idPaciente,
        @NotNull @Future LocalDateTime fecha,
        Especialidad especialidad
) { }
