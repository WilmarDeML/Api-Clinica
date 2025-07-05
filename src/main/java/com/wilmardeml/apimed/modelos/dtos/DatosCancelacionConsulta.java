package com.wilmardeml.apimed.modelos.dtos;

import com.wilmardeml.apimed.modelos.enums.MotivoCancelacion;
import jakarta.validation.constraints.NotNull;

public record DatosCancelacionConsulta(
        @NotNull
        Long idConsulta,
        @NotNull
        MotivoCancelacion motivo
) {
}
