package com.wilmardeml.apimed.modelos.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DatosActualizaMedico(
        @NotNull Long id,
        String nombre,
        @Pattern(regexp = "\\d{10}") String telefono,
        DatosDireccion direccion
) {}
