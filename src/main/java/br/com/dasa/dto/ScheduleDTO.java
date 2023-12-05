package br.com.dasa.dto;

import br.com.dasa.model.Exam;
import br.com.dasa.model.Payment;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@NotNull
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ScheduleDTO{
    private Long idAgendamento;
    private List<Exam> exames;
    private Payment pagamento;

}
