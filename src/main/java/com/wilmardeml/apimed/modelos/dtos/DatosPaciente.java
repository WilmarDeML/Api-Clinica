package com.wilmardeml.apimed.modelos.dtos;

import com.wilmardeml.apimed.modelos.entidades.Paciente;

public record DatosPaciente(
        Long id,
        String nombre,
        String correo,
        String documento
) {
    public DatosPaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getCorreo(),
                paciente.getDocumento()
        );
    }
}
