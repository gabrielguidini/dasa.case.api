package br.com.dasa.controller;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.PaymentDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Payment;
import br.com.dasa.model.enums.ExamEnum;
import br.com.dasa.model.enums.PaymentEnum;
import br.com.dasa.service.ExamService;
import br.com.dasa.service.ScheduleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
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
import org.springframework.web.util.UriComponentsBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ScheduleController.class)
@AutoConfigureMockMvc
@AutoConfigureWebMvc
@Import(ScheduleController.class)
public class ScheduleControllerTest {
    @Autowired private MockMvc mvc;
    @Autowired private ObjectMapper mapper;
    @MockBean private ScheduleService scheduleService;
    @MockBean private ExamService examService;
    private static final String END_POINT_PATH = "/dasa";
    private ScheduleDTO schedule = new ScheduleDTO(
            1L,
            new Exam(new ExamDTO(
                    1L,
                    "string",
                    ExamEnum.IMAGEM,
                    200d
            )),
            new Payment(new PaymentDTO(
                    1L,
                    PaymentEnum.DEBITO,
                    200d
            ))
    );

    @BeforeAll
    static void setup(){

    }
    @Test
    void create_a_schedule_should_return_200() throws Exception {
        String json = """
                {
                  "idAgendamento": 1,
                  "exames": {
                    "idExame": 1,
                    "nomeExame": "string",
                    "tipoExame": "IMAGEM",
                    "valorExame": 0
                  },
                  "pagamento": {
                    "idPagamento": 1,
                    "valorTotal": 0,
                    "tipoPagamento": "DINHEIRO"
                  }
                }
                """;
        scheduleService.createSchedule(schedule,UriComponentsBuilder.fromUriString(END_POINT_PATH));
        var response = mvc.perform(post(END_POINT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json)
        ).andReturn().getResponse();
        Assertions.assertEquals(200,response.getStatus());
    }
    @Test
    void get_all_schedules_should_return_200() throws Exception {
        var response = mvc.perform(get(END_POINT_PATH)
                .contentType(MediaType.APPLICATION_JSON)
        ).andReturn().getResponse();
        Assertions.assertEquals(200, response.getStatus());
    }
    @Test
    void get_exam_by_id_should_return_200() throws Exception {
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
    void add_exam_into_a_schedule_should_return_201() throws Exception {
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
    void delete_exam_from_a_schedule_should_return_200() throws Exception {
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
