package br.com.dasa.controller;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.PaymentDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.UriComponents;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class SchduleControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ScheduleRepository schedule;

    @Test
    void should_return_code_404_when_id_agendamentos_is_not_registered() throws Exception {
        //arrange
        String json = "{}";
        //act
        var response = mvc.perform(
            get("/dasa")
                    .content(json)
                    .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();

        //assert
        Assertions.assertEquals(404,response.getStatus());
    }

    @Test
    void shoud_return_code_201_when_create_a_schedule() throws Exception {
        String json = """
                {
                  "id_agendamento": 0,
                  "exames": {
                    "id_exame": 0,
                    "nome_exame": "string",
                    "tipo_exame": "IMAGEM",
                    "valor_exame": 0
                  },
                  "pagamento": {
                    "id_pagamento": 0,
                    "valor_total": 0,
                    "tipo_pagamento": "DINHEIRO"
                  }
                }
                """;
        var response = mvc.perform(
                post("/dasa")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(201,response.getStatus());
    }

    @Test
    void shoud_return_code_201_when_adding_a_exam() throws Exception {
        String json = """
                {
                  "id_exame": 10,
                  "nome_exame": "string",
                  "tipo_exame": "IMAGEM",
                  "valor_exame": 0
                }
                """;
        var response = mvc.perform(
                put("/dasa/{id_agendamento}")
                        .content(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(201,response.getStatus());
    }

    @Test
    void shoud_return_code_409_when_adding_a_exam_already_reagistered() throws Exception {
        String json = """
                {
                  "id_agendamento": 0,
                  "exames": [
                    {
                      "id_exame": 0,
                      "nome_exame": "string",
                      "tipo_exame": "IMAGEM",
                      "valor_exame": 0
                    }
                  ],
                  "pagamento": {
                    "id_pagamento": 0,
                    "valor_total": 0,
                    "tipo_pagamento": "DINHEIRO"
                  }
                }
                """;
        var response = mvc.perform(
                put("/dasa/{id_agendamento}")
                        .content(json)
                        .accept(json)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(409,response.getStatus());
    }
}