package com.wilmardeml.apimed.modelos.entidades;

import com.wilmardeml.apimed.modelos.dtos.DatosActualizaPaciente;
import com.wilmardeml.apimed.modelos.dtos.DatosRegistroPaciente;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@Entity
@Table(name = "pacientes")
public class Paciente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean activo;
    private String nombre;
    private String correo;
    private String telefono;
    private String documento;

    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente paciente) {
        activo = Boolean.TRUE;
        nombre = paciente.getNombre();
        correo = paciente.getCorreo();
        telefono = paciente.getTelefono();
        documento = paciente.getDocumento();
        direccion = new Direccion(paciente.getDireccion());
    }

    public void actualizarInfo(DatosActualizaPaciente paciente) {
        if (paciente.direccion() != null) direccion.actualizarInfo(paciente.direccion());
        if (paciente.nombre() != null) nombre = paciente.nombre();
        if (paciente.telefono() != null) telefono = paciente.telefono();
    }

    public void desactivar() {
        activo = Boolean.FALSE;
    }

}
