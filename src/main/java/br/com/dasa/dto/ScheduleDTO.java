package br.com.dasa.dto;

import br.com.dasa.model.Exam;
import br.com.dasa.model.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@NotNull
@Getter
@Setter
public class ScheduleDTO{
    private Long idAgendamento;
    private List<Exam> exames;
    private Payment pagamento;

    public ScheduleDTO(Long idAgendamento, List<Exam> exames, Payment pagamento) {
        this.idAgendamento = idAgendamento;
        this.exames = exames;
        this.pagamento = pagamento;
    }

    public ScheduleDTO() {
    }
}
