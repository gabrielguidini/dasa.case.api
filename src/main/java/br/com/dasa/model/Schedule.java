package br.com.dasa.model;


import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;

@Entity(name = "agendamento")
@Data
@Getter
@Setter
@Embeddable
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_agendamento;
    @OneToMany(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn()
    private Collection<Exam> exames = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private Payment pagamento;

    @JsonCreator
    public Schedule(ScheduleDTO scheduleDTO){
        this.id_agendamento = scheduleDTO.id_agendamento();
        this.pagamento = scheduleDTO.pagamento();
        exames.add(scheduleDTO.exames());
        pagamento.setValor_total(updateTotalValue().getValor_total());
    }

    public Schedule(){}

    public void addExame(ExamDTO exame) {
        exames.add(new Exam(exame));
    }

    public void deleteExame(ExamDTO exame){
        exames.remove(new Exam(exame));
    }

    public Payment updateTotalValue() {
        double i = 0;
        for (Exam exam:exames) {
            i += exam.getValor_exame().doubleValue();
            pagamento.setValor_total(i);
        }
        return pagamento;
    }
}
