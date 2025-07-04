package com.wilmardeml.apimed.controladores;

import com.wilmardeml.apimed.modelos.dtos.DatosDetalleMedico;
import com.wilmardeml.apimed.modelos.dtos.DatosActualizaMedico;
import com.wilmardeml.apimed.modelos.dtos.DatosMedico;
import com.wilmardeml.apimed.modelos.dtos.DatosRegistroMedico;
import com.wilmardeml.apimed.modelos.entidades.Medico;
import com.wilmardeml.apimed.repositorios.MedicoRepository;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("medicos")
public class MedicoController {
    private final MedicoRepository repository;

    public MedicoController(MedicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public ResponseEntity<Page<DatosMedico>> listar(@PageableDefault(size = 10, sort = "nombre") Pageable paginacion) {
        var page = repository.findAllByActivoTrue(paginacion).map(DatosMedico::new);

        return ResponseEntity.ok(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<DatosDetalleMedico> buscar(@PathVariable Long id) {
        var medicoBuscado = repository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetalleMedico(medicoBuscado));
    }

    @Transactional
    @PostMapping
    public ResponseEntity<DatosDetalleMedico> registrar(@RequestBody @Valid DatosRegistroMedico datosMedico) {
        var medico = new Medico(datosMedico);
        repository.save(medico);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(medico.getId())
                .toUri();

        return ResponseEntity.created(location).body(new DatosDetalleMedico(medico));
    }

    @Transactional
    @PutMapping
    public ResponseEntity<DatosDetalleMedico> actualizar(@RequestBody @Valid DatosActualizaMedico medico) {
        var medicoBuscado = repository.getReferenceById(medico.id());
        medicoBuscado.actualizarInfo(medico);

        return ResponseEntity.ok(new DatosDetalleMedico(medicoBuscado));
    }

    @Transactional
    @DeleteMapping("{id}")
    public ResponseEntity<?> desactivar(@PathVariable Long id) {
        var medicoBuscado = repository.getReferenceById(id);
        medicoBuscado.desactivar();

        return ResponseEntity.noContent().build();
    }
}
