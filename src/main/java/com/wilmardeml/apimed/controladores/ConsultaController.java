package com.wilmardeml.apimed.controladores;

import com.wilmardeml.apimed.modelos.dtos.DatosCancelacionConsulta;
import com.wilmardeml.apimed.modelos.dtos.DatosDetalleConsulta;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import com.wilmardeml.apimed.servicios.ReservaConsultasService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("consultas")
@SecurityRequirement(name = "bearer-key")
public class ConsultaController {
    private final ReservaConsultasService consultasService;

    public ConsultaController(ReservaConsultasService consultasService) {
        this.consultasService = consultasService;
    }


    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetalleConsulta> registrar(@RequestBody @Valid DatosReservaConsulta datosReserva) {

        DatosDetalleConsulta consulta = consultasService.reservar(datosReserva);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(consulta.id())
                .toUri();

        return ResponseEntity.created(location).body(consulta);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity<?> cancelar(@RequestBody @Valid DatosCancelacionConsulta datosCancelacion) {
        consultasService.cancelar(datosCancelacion);
        return ResponseEntity.noContent().build();
    }

}
