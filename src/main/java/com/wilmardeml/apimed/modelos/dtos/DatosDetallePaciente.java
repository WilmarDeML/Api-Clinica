package com.wilmardeml.apimed.modelos.dtos;

import com.wilmardeml.apimed.modelos.entidades.Direccion;
import com.wilmardeml.apimed.modelos.entidades.Paciente;

public record DatosDetallePaciente(
        Long id,
        String nombre,
        String correo,
        String telefono,
        String documento,
        Direccion direccion
) {
    public DatosDetallePaciente(Paciente paciente) {
        this(
                paciente.getId(),
                paciente.getNombre(),
                paciente.getCorreo(),
                paciente.getTelefono(),
                paciente.getDocumento(),
                paciente.getDireccion()
        );
    }
}
