package br.com.dasa.dto;

import br.com.dasa.model.enums.ExamEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ExamDTO{
    @NotNull
    private Long idExame;
    @NotBlank
    private String nomeExame;
    @NotNull
    private ExamEnum tipoExame;
    @NotNull
    private Double valorExame;

    public ExamDTO(Long idExame, String nomeExame, ExamEnum tipoExame, Double valorExame) {
        this.idExame = idExame;
        this.nomeExame = nomeExame;
        this.tipoExame = tipoExame;
        this.valorExame = valorExame;
    }

    public ExamDTO() {}
}
