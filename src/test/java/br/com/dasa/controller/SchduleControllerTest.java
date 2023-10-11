package br.com.dasa.controller;

import br.com.dasa.ApiApplication;
import br.com.dasa.model.Exam;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import br.com.dasa.service.ExamService;
import br.com.dasa.service.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
class SchduleControllerTest {
    //injetar o service
    @Autowired
    private MockMvc mvc;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ExamService examService;
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

    @Test
    void delete_exam_should_return_code_201() throws Exception {
        Long id = 1L;
        String json = """
                {
                  "id_exame": 4,
                  "nome_exame": "string",
                  "tipo_exame": "IMAGEM",
                  "valor_exame": 0
                }
                """;
        var response = mvc.perform(
                delete("/dasa/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
        ).andReturn().getResponse();
        Assertions.assertEquals(404,response.getStatus());
    }

    @Test
    void delete_exam_should_return_404_if_doesnt_exists() throws Exception {
        long id = 1;
        String json = """
             {
               "id_exame": 1,
               "nome_exame": "string",
               "tipo_exame": "IMAGEM",
               "valor_exame": 0
             }
             """;
        if(examService.getExamById(id)){
             var response = mvc.perform(
                     delete("/dasa/")
                             .contentType(MediaType.APPLICATION_JSON)
                             .content(json)
             ).andReturn().getResponse();
             Assertions.assertEquals(404, response.getStatus());
        }
    }
}