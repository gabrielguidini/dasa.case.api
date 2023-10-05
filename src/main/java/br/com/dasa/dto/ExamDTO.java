package br.com.dasa.dto;

import br.com.dasa.model.enums.ExamEnum;
import jakarta.validation.constraints.NotNull;

@NotNull
public record ExamDTO(
        Long id_exame,
        String nome_exame,
        ExamEnum tipo_exame,
        Double valor_exame
) {
}
