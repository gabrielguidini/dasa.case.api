package br.com.dasa.dto;

import br.com.dasa.model.enums.ExamEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ExamDTO {
    @NotNull
    private UUID idExame = UUID.randomUUID();
    @NotBlank
    private String nomeExame;
    @NotNull
    private ExamEnum tipoExame;
    @NotNull
    private Double valorExame;

}