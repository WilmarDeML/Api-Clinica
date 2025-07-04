package com.wilmardeml.apimed.modelos.dtos;

import com.wilmardeml.apimed.modelos.entidades.Medico;
import com.wilmardeml.apimed.modelos.enums.Especialidad;

public record DatosMedico(
        Long id,
        String nombre,
        String correo,
        String documento,
        Especialidad especialidad
) {
    public DatosMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getCorreo(),
                medico.getDocumento(),
                medico.getEspecialidad()
        );
    }
}
