package com.wilmardeml.apimed.modelos.dtos;

import com.wilmardeml.apimed.modelos.enums.Especialidad;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DatosRegistroMedico extends DatosRegistroPersonaCommon {

        @NotNull(message = "{especialidad.obligatorio}")
        Especialidad especialidad;
}
