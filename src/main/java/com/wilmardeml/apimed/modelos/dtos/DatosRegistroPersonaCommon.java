package com.wilmardeml.apimed.modelos.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DatosRegistroPersonaCommon {
        @NotBlank(message = "{nombre.obligatorio}")
        String nombre;

        @NotBlank(message = "{email.obligatorio}")
        @Email(message = "{email.invalido}")
        String correo;

        @NotBlank(message = "{telefono.obligatorio}")
        @Pattern(regexp = "\\d{10}", message = "{telefono.invalido}")
        String telefono;

        @NotBlank(message = "{documento.obligatorio}")
        @Pattern(regexp = "\\d{7,10}", message = "{documento.invalido}")
        String documento;

        @NotNull(message = "{direccion.obligatorio}")
        @Valid DatosDireccion direccion;
}
