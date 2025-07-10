package com.wilmardeml.apimed.controladores;

import com.wilmardeml.apimed.modelos.dtos.DatosDetalleMedico;
import com.wilmardeml.apimed.modelos.dtos.DatosDireccion;
import com.wilmardeml.apimed.modelos.dtos.DatosRegistroMedico;
import com.wilmardeml.apimed.modelos.entidades.Direccion;
import com.wilmardeml.apimed.modelos.entidades.Medico;
import com.wilmardeml.apimed.modelos.enums.Especialidad;
import com.wilmardeml.apimed.repositorios.MedicoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
class MedicoControllerTest {
    private final MockMvc mockMvc;

    @MockitoBean
    private MedicoRepository repository;

    @Autowired
    private JacksonTester<DatosRegistroMedico> datosRegistroMedicoJson;

    @Autowired
    private JacksonTester<DatosDetalleMedico> datosDetalleMedicoJson;

    @Autowired
    MedicoControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void registrarMedicoSinBodyRespondeStatus400() throws Exception {
        var resp = mockMvc.perform(post("/medicos")).andReturn().getResponse();

        assertThat(resp.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void registrarMedicoConBodyValidoRespondeStatus200() throws Exception {
        // Given (dado el contexto)
        var datosDireccion = datosDireccion();
        var medico = new Medico(
                null, true, "Médico 1", "medico1@mail.com", "3008882121",
                "1000000", Especialidad.CARDIOLOGIA, new Direccion(datosDireccion)
        );

        when(repository.save(any())).thenReturn(medico);

        // When (cuando se consume el endpoint a testear)
        var response = mockMvc.perform(post("/medicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosRegistroMedicoJson
                                .write(new DatosRegistroMedico("Médico 1", "medico1@mail.com",
                                        "3008882121", "1000000", Especialidad.CARDIOLOGIA,
                                        datosDireccion))
                                .getJson()))
                .andReturn()
                .getResponse();

        var jsonEsperado = datosDetalleMedicoJson.write(new DatosDetalleMedico(medico)).getJson();

        // Then (luego se valida la respuesta)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

    private DatosDireccion datosDireccion() {
        return new DatosDireccion(
                "Calle x",
                10L,
                "complemento",
                "barrio",
                "ciuedad",
                "estado",
                "123456"
        );
    }
}