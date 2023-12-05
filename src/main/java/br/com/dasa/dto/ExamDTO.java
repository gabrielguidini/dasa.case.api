package br.com.dasa.dto;

import br.com.dasa.model.enums.ExamEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ExamDTO {
    @NotNull
    private Long idExame;
    @NotBlank
    private String nomeExame;
    @NotNull
    private ExamEnum tipoExame;
    @NotNull
    private Double valorExame;

}