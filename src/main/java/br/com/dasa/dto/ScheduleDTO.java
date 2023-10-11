package br.com.dasa.dto;

import br.com.dasa.model.Exam;
import br.com.dasa.model.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@NotNull
@Getter
@Setter
public class ScheduleDTO{
    private Long idAgendamento;
    private Exam exames;
    private Payment pagamento;

    public ScheduleDTO(Long idAgendamento, Exam exames, Payment pagamento) {
        this.idAgendamento = idAgendamento;
        this.exames = exames;
        this.pagamento = pagamento;
    }

    public ScheduleDTO() {
    }
}
