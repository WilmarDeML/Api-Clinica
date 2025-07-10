package com.wilmardeml.apimed.repositorios;

import com.wilmardeml.apimed.modelos.dtos.DatosDireccion;
import com.wilmardeml.apimed.modelos.dtos.DatosRegistroMedico;
import com.wilmardeml.apimed.modelos.dtos.DatosRegistroPaciente;
import com.wilmardeml.apimed.modelos.entidades.Consulta;
import com.wilmardeml.apimed.modelos.entidades.Medico;
import com.wilmardeml.apimed.modelos.entidades.Paciente;
import com.wilmardeml.apimed.modelos.enums.Especialidad;
import jakarta.persistence.EntityManager;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
class MedicoRepositoryTest {

    private final MedicoRepository medicoRepository;
    private final EntityManager entityManager;

    @Autowired
    MedicoRepositoryTest(MedicoRepository medicoRepository, EntityManager entityManager) {
        this.medicoRepository = medicoRepository;
        this.entityManager = entityManager;
    }

    @Test
    void elegirMedicoAleatorioNoDisponibleEnFecha() {
        // Given (dado el contexto)
        var siguienteLunes10AM = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = registrarMedico("Medico1", "medico1@email.com", "1234567", Especialidad.CARDIOLOGIA);
        var paciente = registrarPaciente("Paciente1", "paciente1@email.com", "12345678");
        registrarConsulta(medico, paciente, siguienteLunes10AM);

        // When (cuando se ejecuta la función a testear)
        var medicoLibre = medicoRepository
                .elegirMedicoAleatorioDisponibleEnFecha(Especialidad.CARDIOLOGIA, siguienteLunes10AM);

        // Then (luego se valida la respuesta)
        Assertions.assertThat(medicoLibre).isNull();
    }

    @Test
    void elegirMedicoAleatorioDisponibleEnFecha() {
        // Given (dado el contexto)
        var siguienteLunes10AM = LocalDate.now().with(TemporalAdjusters.next(DayOfWeek.MONDAY)).atTime(10, 0);
        var medico = registrarMedico("Medico1", "medico1@email.com", "1234567", Especialidad.CARDIOLOGIA);

        // When (cuando se ejecuta la función a testear)
        var medicoLibre = medicoRepository
                .elegirMedicoAleatorioDisponibleEnFecha(Especialidad.CARDIOLOGIA, siguienteLunes10AM);

        // Then (luego se valida la respuesta)
        Assertions.assertThat(medicoLibre).isEqualTo(medico);
    }

    private void registrarConsulta(Medico medico, Paciente paciente, LocalDateTime fecha) {
        entityManager.persist(new Consulta(null, medico, paciente, fecha, null));
    }

    private Medico registrarMedico(String nombre, String correo, String documento, Especialidad especialidad) {
        var medico = new Medico(datosRegistroMedico(nombre, correo, documento, especialidad));
        return medicoRepository.save(medico);
    }

    private Paciente registrarPaciente(String nombre, String correo, String documento) {
        var paciente = new Paciente(datosRegistroPaciente(nombre, correo, documento));
        entityManager.persist(paciente);
        return paciente;
    }

    private DatosRegistroMedico datosRegistroMedico(String nombre, String correo, String documento, Especialidad e) {
        return new DatosRegistroMedico(
                nombre,
                correo,
                "1234567890",
                documento,
                e,
                datosDireccion()
        );
    }

    private DatosRegistroPaciente datosRegistroPaciente(String nombre, String correo, String documento) {
        return new DatosRegistroPaciente(
                nombre,
                correo,
                "1234567890",
                documento,
                datosDireccion()
        );
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "Calle x",
                10L,
                "complemento",
                "barrio",
                "ciuedad",
                "estado",
                "codPostal"
        );
    }
}