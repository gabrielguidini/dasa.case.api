package br.com.dasa.controller;

import br.com.dasa.ApiApplication;
import br.com.dasa.repository.ScheduleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class SchduleControllerTest {
    @Autowired
    private MockMvc mvc;
    @Autowired
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
        Assertions.assertEquals(200,response.getStatus());
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
}