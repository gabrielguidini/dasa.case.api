package br.com.dasa.model;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.model.enums.ExamEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Entity(name = "exames")
@Data
@Getter
@Setter
public class Exam {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_exame;
    @NotEmpty
    private String nome_exame;
    @NotEmpty
    @Enumerated(EnumType.STRING)
    private ExamEnum tipo_exame;
    @NotNull
    private Double valor_exame;


    @JsonCreator
    public Exam(ExamDTO examDTO){
        this.id_exame = examDTO.id_exame();
        this.nome_exame = examDTO.nome_exame();
        this.tipo_exame = examDTO.tipo_exame();
        setValor_exame(checkTypeValue(tipo_exame));
    }

    public Exam() {}

    public Double checkTypeValue(ExamEnum examEnum){
        return examEnum.equals(ExamEnum.IMAGEM)?200.0:50.0;
    }
}
