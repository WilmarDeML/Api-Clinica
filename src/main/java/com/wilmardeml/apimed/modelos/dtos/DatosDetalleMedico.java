package com.wilmardeml.apimed.modelos.dtos;

import com.wilmardeml.apimed.modelos.entidades.Direccion;
import com.wilmardeml.apimed.modelos.entidades.Medico;
import com.wilmardeml.apimed.modelos.enums.Especialidad;

public record DatosDetalleMedico(
        Long id,
        String nombre,
        String correo,
        String telefono,
        String documento,
        Especialidad especialidad,
        Direccion direccion
) {
    public DatosDetalleMedico(Medico medico) {
        this(
                medico.getId(),
                medico.getNombre(),
                medico.getCorreo(),
                medico.getTelefono(),
                medico.getDocumento(),
                medico.getEspecialidad(),
                medico.getDireccion()
        );
    }
}
