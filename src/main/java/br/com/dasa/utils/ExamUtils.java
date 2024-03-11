package br.com.dasa.utils;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.model.Exam;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ExamUtils {

    public ExamDTO convertPaymentToDto (Exam exam) {
        return ExamDTO.builder()
                .idExame(exam.getIdExame())
                .tipoExame(exam.getTipoExame())
                .valorExame(exam.getValorExame() == null ? null : exam.getValorExame())
                .nomeExame(exam.getNomeExame())
                .build();
    }
}
