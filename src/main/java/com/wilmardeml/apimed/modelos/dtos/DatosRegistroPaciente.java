package com.wilmardeml.apimed.modelos.dtos;

public class DatosRegistroPaciente extends DatosRegistroPersonaCommon {
    public DatosRegistroPaciente(String nombre, String correo, String telefono, String documento,
                                 DatosDireccion direccion) {
        super(nombre, correo, telefono, documento, direccion);
    }
}
