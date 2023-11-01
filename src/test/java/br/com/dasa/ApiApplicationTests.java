package br.com.dasa;

import br.com.dasa.arrange.ExamArrange;
import br.com.dasa.arrange.ScheduleArrange;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import br.com.dasa.service.implementation.ExamService;
import br.com.dasa.service.implementation.ScheduleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class ApiApplicationTests {
	@InjectMocks
	private ScheduleService scheduleService;
	@MockBean
	private ExamService examService;
	@Mock
	private ExamRepository examRepository;
	@Mock
	private ScheduleRepository scheduleRepository;
	private final String END_POINT_PATH = "/dasa";

	@Test
	void addExamIntoSchedule() {
		//arrange
		Schedule schedule = ScheduleArrange.getValidSchedule();
		Exam exam =  ExamArrange.getValidExam();
		schedule.addExameIntoSchedule(exam);
		scheduleService.updateValueAndType(schedule);
		//act
		boolean existsCheck = false;
		for (Exam exam1 : schedule.getExames()) {
			if (exam1.getIdExame().equals(schedule.getExames().iterator().next().getIdExame())) {
				existsCheck = true;
			}
		}
		//assert
		assertTrue(existsCheck);
	}

	@Test
	void removeExamFromSchedule(){
		//arrange
		Schedule schedule = ScheduleArrange.getValidSchedule();
		Exam exam =  ExamArrange.getValidExam();
		schedule.addExameIntoSchedule(exam);
		scheduleService.updateValueAndType(schedule);
		schedule.removeExamFromList(exam);
		scheduleService.updateValueAndType(schedule);
		//act
		boolean existsCheck = exam.getIdExame().equals(schedule.getExames().iterator().next().getIdExame());
        //assert
		assertFalse(existsCheck);
	}

	@Test
	void updatingPaymentValueAndTypeInSchedule(){
		//arrange
		Schedule schedule = ScheduleArrange.getValidSchedule();
		Exam exam =  ExamArrange.getValidExam();
		schedule.addExameIntoSchedule(exam);
		scheduleService.updateValueAndType(schedule);
		//act
		var response = schedule.getExames().stream().map(Exam::getValorExame).reduce(0d, Double::sum);
		//assert
		assertEquals(response,schedule.getPagamento().getValorTotal());
	}

	//service methods tests
	@Test
	void creatingSchedule(){
		//arrange
		Schedule schedule = ScheduleArrange.getValidSchedule();
		//act
		var actualResponse = scheduleService.createSchedule(new ScheduleDTO(schedule.getIdAgendamento()
										,schedule.getExames()
										,schedule.getPagamento())
				, UriComponentsBuilder.fromUriString(END_POINT_PATH)).getStatusCode();
		//assert
		assertTrue(actualResponse.isSameCodeAs(HttpStatusCode.valueOf(201)));
	}
}