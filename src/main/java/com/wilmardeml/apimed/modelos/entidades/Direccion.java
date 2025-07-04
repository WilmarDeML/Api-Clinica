package com.wilmardeml.apimed.modelos.entidades;

import com.wilmardeml.apimed.modelos.dtos.DatosDireccion;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Embeddable
public class Direccion {
    private String calle;
    private Long numero;
    private String complemento;
    private String barrio;
    private String ciudad;
    private String estado;
    private String codPostal;

    public Direccion(DatosDireccion direccion) {
        calle = direccion.calle();
        numero = direccion.numero();
        complemento = direccion.complemento();
        barrio = direccion.barrio();
        ciudad = direccion.ciudad();
        estado = direccion.estado();
        codPostal = direccion.codPostal();
    }

    public void actualizarInfo(DatosDireccion direccion) {
        if (direccion.calle() != null) calle = direccion.calle();
        if (direccion.numero() != null) numero = direccion.numero();
        if (direccion.complemento() != null) complemento = direccion.complemento();
        if (direccion.barrio() != null) barrio = direccion.barrio();
        if (direccion.ciudad() != null) ciudad = direccion.ciudad();
        if (direccion.estado() != null) estado = direccion.estado();
        if (direccion.codPostal() != null) codPostal = direccion.codPostal();
    }
}
