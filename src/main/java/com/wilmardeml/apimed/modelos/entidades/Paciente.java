package com.wilmardeml.apimed.modelos.entidades;

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

    private String nombre;
    private String correo;
    private String telefono;
    private String documento;

    @Embedded
    private Direccion direccion;

    public Paciente(DatosRegistroPaciente paciente) {
        nombre = paciente.getNombre();
        correo = paciente.getCorreo();
        telefono = paciente.getTelefono();
        documento = paciente.getDocumento();
        direccion = new Direccion(paciente.getDireccion());
    }

}
