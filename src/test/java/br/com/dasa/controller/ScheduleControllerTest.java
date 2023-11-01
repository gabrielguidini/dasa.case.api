package br.com.dasa.controller;

import br.com.dasa.service.implementation.ExamService;
import br.com.dasa.service.implementation.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@Import(ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired private MockMvc mvc;
    @MockBean private ScheduleService scheduleService;
    @MockBean private ExamService examService;
    private static final String END_POINT_PATH = "/dasa";

    @Test
    void creatingScheduleShouldReturnHttpOK() throws Exception {
        //arrange
        String json =
                """
                {
                  "idAgendamento": 0,
                  "exames": [
                    {
                      "idExame": 0,
                      "nomeExame": "string",
                      "tipoExame": "IMAGEM",
                      "valorExame": 0
                    }
                  ],
                  "pagamento": {
                    "idPagamento": 0,
                    "valorTotal": 0,
                    "tipoPagamento": "DINHEIRO"
                  }
                }
                """;
        //act
        var response = mvc.perform(post(END_POINT_PATH)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andReturn()
                .getResponse();
        //assert
        assertEquals(200,response.getStatus());
    }

    @Test
    void getAllSchedulesShouldReturn200() throws Exception {
        var response = mvc.perform(get(END_POINT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        assertEquals(200, response.getStatus());
    }
    @Test
    void getExamByIdShouldReturn200() throws Exception {
        LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idAgendamento", "1");
        String uri = END_POINT_PATH +"/"+ 1;
        mvc.perform(
                get(uri)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void addExamIntoScheduleShouldReturn201() throws Exception {
        LinkedMultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idAgendamento", "1");
        String uri = END_POINT_PATH +"/"+ 1;
        String examJson = """
                {
                  "idExame": 2,
                  "nomeExame": "string",
                  "tipoExame": "IMAGEM",
                  "valorExame": 0
                }
                """;
        mvc.perform(
                put(uri)
                        .params(requestParams)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(examJson)
        ).andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    void deleteExamFromScheduleShouldReturn200() throws Exception {
        LinkedMultiValueMap<String,String> requestParams = new LinkedMultiValueMap<>();
        requestParams.add("idAgendamento", "1");
        String uri = END_POINT_PATH +"/"+ 1;
        String examJson = """
                {
                  "idExame": 2,
                  "nomeExame": "string",
                  "tipoExame": "IMAGEM",
                  "valorExame": 0
                }
                """;
        mvc.perform(
                        delete(uri)
                                .params(requestParams)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(examJson)
                ).andExpect(status().isOk())
                .andDo(print());
    }
}
