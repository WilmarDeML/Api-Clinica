package com.wilmardeml.apimed.modelos.entidades;

import com.wilmardeml.apimed.modelos.dtos.DatosActualizaMedico;
import com.wilmardeml.apimed.modelos.dtos.DatosRegistroMedico;
import com.wilmardeml.apimed.modelos.enums.Especialidad;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "medicos")
public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean activo;
    private String nombre;
    private String correo;
    private String telefono;
    private String documento;

    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;

    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico medico) {
        activo = Boolean.TRUE;
        nombre = medico.getNombre();
        correo = medico.getCorreo();
        telefono = medico.getTelefono();
        documento = medico.getDocumento();
        especialidad = medico.getEspecialidad();
        direccion = new Direccion(medico.getDireccion());
    }

    public void actualizarInfo(DatosActualizaMedico medico) {
        if (medico.direccion() != null) direccion.actualizarInfo(medico.direccion());
        if (medico.nombre() != null) nombre = medico.nombre();
        if (medico.telefono() != null) telefono = medico.telefono();
    }

    public void desactivar() {
        activo = Boolean.FALSE;
    }
}
