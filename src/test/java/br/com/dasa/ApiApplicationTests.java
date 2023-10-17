package br.com.dasa;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.PaymentDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Payment;
import br.com.dasa.model.Schedule;
import br.com.dasa.model.enums.ExamEnum;
import br.com.dasa.model.enums.PaymentEnum;
import br.com.dasa.service.ScheduleService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class ApiApplicationTests {
	@MockBean
	private ScheduleService scheduleService;
	private Schedule schedule = new Schedule(new ScheduleDTO(
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
	));

	@Test
	void add_exam_into_a_schedule() {
		ExamDTO examDTO = new ExamDTO(
				2L,
				"testing",
				ExamEnum.COLETA,
				50d
		);
		schedule.addExame(examDTO);
		boolean existsCheck = false;
		for (Exam exam : schedule.getExames()) {
			if (exam.getIdExame().equals(examDTO.getIdExame())) {
				existsCheck = true;
			}
		}
		Assertions.assertTrue(existsCheck);
	}
	@Test
	void remove_exam_from_a_schedule(){
		ExamDTO examDTO = new ExamDTO(
				2L,
				"testing",
				ExamEnum.COLETA,
				50d
		);
		schedule.removeExamFromList(new Exam(examDTO));
		Assertions.assertEquals(2L,examDTO.getIdExame());
	}
	@Test
	void payment_total_expect(){
		//action
		ExamDTO examDTO = new ExamDTO(
				2L,
				"testing",
				ExamEnum.COLETA,
				50d
		);
		//act
		schedule.addExame(examDTO);
		Payment expect = schedule.updateTotalValueAndType();
		//asert
		Assertions.assertEquals(250d,expect.getValorTotal());
	}
}
