package com.wilmardeml.apimed.modelos.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DatosDireccion(
     @NotBlank String calle,
     Long numero,
     String complemento,
     @NotBlank String barrio,
     @NotBlank String ciudad,
     @NotBlank String estado,
     @NotBlank @Pattern(regexp = "\\d{4,8}") String codPostal
) {}
