package com.wilmardeml.apimed.modelos.dtos;

import jakarta.validation.constraints.NotBlank;

public record DatosLogin(
        @NotBlank String username,
        @NotBlank String pass
) {
}
