package br.com.dasa.dto;

import br.com.dasa.model.Exam;
import br.com.dasa.model.Payment;
import jakarta.validation.constraints.NotNull;
@NotNull
public record ScheduleDTO(
        Long id_agendamento,
        Exam exames,
        Payment pagamento
) {
}
