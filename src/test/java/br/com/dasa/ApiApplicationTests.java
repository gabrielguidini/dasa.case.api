package br.com.dasa;

import br.com.dasa.arrange.ExamArrange;
import br.com.dasa.arrange.ScheduleArrange;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import br.com.dasa.service.implementation.ExamService;
import br.com.dasa.service.implementation.ScheduleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ApiApplicationTests {
	@InjectMocks
	private ScheduleService scheduleService;
	@MockBean
	private ExamService examService;
	@Mock
	private ExamRepository examRepository;
	@Mock
	private ScheduleRepository scheduleRepository;

	@Test
	void addExamIntoSchedule() {
		//arrange
		Schedule schedule = ScheduleArrange.getValidSchedule();
		Exam exam =  ExamArrange.getValidExam();
		schedule.addExame(exam);
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
		schedule.addExame(exam);
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
		schedule.addExame(exam);
		scheduleService.updateValueAndType(schedule);
		//act
		var response = schedule.getExames().stream().map(Exam::getValorExame).reduce(0d, Double::sum);
		System.out.println(response);
		//assert
		assertEquals(response,schedule.getPagamento().getValorTotal());
	}
}
