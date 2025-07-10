package com.wilmardeml.apimed.controladores;

import com.wilmardeml.apimed.modelos.dtos.DatosDetalleConsulta;
import com.wilmardeml.apimed.modelos.dtos.DatosReservaConsulta;
import com.wilmardeml.apimed.modelos.enums.Especialidad;
import com.wilmardeml.apimed.servicios.ReservaConsultasService;
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

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@WithMockUser
class ConsultaControllerTest {

    private final MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosReservaConsulta> datosReservaConsultaJson;

    @Autowired
    private JacksonTester<DatosDetalleConsulta> datosDetalleConsultaJson;

    @MockitoBean
    private ReservaConsultasService reservaDeConsultas;

    @Autowired
    ConsultaControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    void registrarConsultaSinBodyRespondeStatus400() throws Exception {
        var resp = mockMvc.perform(post("/consultas")).andReturn().getResponse();

        assertThat(resp.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    void registrarConsultaConBodyValidoRespondeStatus200() throws Exception {

        // Given (dado el contexto)
        var fechaConsulta = LocalDateTime.now().plusHours(1);
        var datosDetalle = new DatosDetalleConsulta(null, 2L, 5L, fechaConsulta);

        when(reservaDeConsultas.reservar(any())).thenReturn(datosDetalle);

        // When (cuando se consume el endpoint a testear)
        var response = mockMvc.perform(post("/consultas")
                .contentType(MediaType.APPLICATION_JSON)
                .content(datosReservaConsultaJson
                        .write(new DatosReservaConsulta(2L, 5L, fechaConsulta, Especialidad.CARDIOLOGIA))
                        .getJson()))
                .andReturn()
                .getResponse();

        var jsonEsperado = datosDetalleConsultaJson.write(datosDetalle).getJson();

        // Then (luego se valida la respuesta)
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }
}