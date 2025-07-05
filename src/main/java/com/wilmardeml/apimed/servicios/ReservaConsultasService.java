package com.wilmardeml.apimed.servicios;

import com.wilmardeml.apimed.infra.excepciones.ValidacionException;
import com.wilmardeml.apimed.modelos.dtos.DatosCancelacionConsulta;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import com.wilmardeml.apimed.modelos.entidades.Consulta;
import com.wilmardeml.apimed.modelos.entidades.Medico;
import com.wilmardeml.apimed.repositorios.ConsultaRepository;
import com.wilmardeml.apimed.repositorios.MedicoRepository;
import com.wilmardeml.apimed.repositorios.PacienteRepository;
import com.wilmardeml.apimed.servicios.validaciones.ValidadorDeConsultas;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservaConsultasService {
    private final ConsultaRepository repository;
    private final MedicoRepository medicoRepository;
    private final PacienteRepository pacienteRepository;
    private final List<ValidadorDeConsultas> validadores;

    public ReservaConsultasService(ConsultaRepository repository, MedicoRepository medicoRepository,
                                   PacienteRepository pacienteRepository, List<ValidadorDeConsultas> validadores) {
        this.repository = repository;
        this.medicoRepository = medicoRepository;
        this.pacienteRepository = pacienteRepository;
        this.validadores = validadores;
    }

    public Consulta reservar(DatosReservaConsulta datosReserva) {

        if (!pacienteRepository.existsById(datosReserva.idPaciente()))
            throw new ValidacionException("No existe un paciente con el id informado");

        if (datosReserva.idMedico() != null && !medicoRepository.existsById(datosReserva.idMedico()))
            throw new ValidacionException("No existe un paciente con el id informado");

        validadores.forEach(validador -> validador.validar(datosReserva));

        var medico = elegirMedico(datosReserva);

        if (medico == null)
            throw new ValidacionException("No existe un médico disponible en ese horario");

        var paciente = pacienteRepository.getReferenceById(datosReserva.idPaciente());
        var consulta = new Consulta(null, medico, paciente, datosReserva.fecha(), null);
        return repository.save(consulta);
    }

    private Medico elegirMedico(DatosReservaConsulta datosReserva) {
        if (datosReserva.idMedico() != null)
            return medicoRepository.getReferenceById(datosReserva.idMedico());

        if (datosReserva.especialidad() == null)
            throw new ValidacionException("Es necesario elegir una especialidad cuando no se elige un médico");

        return medicoRepository.elegirMedicoAleatorioDisponibleEnFecha(datosReserva.especialidad(), datosReserva.fecha());
    }

    public void cancelar(DatosCancelacionConsulta datos) {
        if (!repository.existsById(datos.idConsulta())) {
            throw new ValidacionException("Id de la consulta informado no existe!");
        }
        var consulta = repository.getReferenceById(datos.idConsulta());
        consulta.cancelar(datos.motivo());
    }
}
