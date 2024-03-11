package br.com.dasa.model;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.model.enums.ExamEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity(name = "exams")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Exam {
    @Id @Builder.Default
    private UUID idExame = UUID.randomUUID();
    @NotEmpty
    private String nomeExame;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private ExamEnum tipoExame;
    @NotNull
    private Double valorExame;


    @JsonCreator
    public Exam(ExamDTO examDTO){
        this.idExame = examDTO.getIdExame();
        this.nomeExame = examDTO.getNomeExame();
        this.tipoExame = examDTO.getTipoExame();
        this.valorExame = examDTO.getValorExame();
        setValorExame(checkTypeValue(tipoExame));
    }

    public Double checkTypeValue(ExamEnum examEnum){
        return examEnum.equals(ExamEnum.IMAGEM)?200.0:50.0;
    }
}
