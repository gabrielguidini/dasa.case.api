package br.com.dasa;

import br.com.dasa.controller.SchduleController;
import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.PaymentDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Payment;
import br.com.dasa.model.Schedule;
import br.com.dasa.model.enums.ExamEnum;
import br.com.dasa.model.enums.PaymentEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApiApplicationTests {
	@Test
	void payment_total_expect_200(){
		//arrange
		Schedule schedule = new Schedule(new ScheduleDTO(
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
		//act
		Payment expect = schedule.updateTotalValueAndType();
		//asert
		Assertions.assertEquals(schedule.getPagamento(),expect);
	}
}
