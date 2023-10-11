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
@Getter
@Setter
@Embeddable
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;
    @OneToMany(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn()
    private Collection<Exam> exames = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private Payment pagamento;

    @JsonCreator
    public Schedule(ScheduleDTO scheduleDTO){
        this.idAgendamento = scheduleDTO.getIdAgendamento();
        this.pagamento = scheduleDTO.getPagamento();
        exames.add(scheduleDTO.getExames());
        pagamento.setValorTotal(updateTotalValueAndType().getValorTotal());
    }

    public Schedule(){}

    public void addExame(ExamDTO exame) {
        exames.add(new Exam(exame));
    }

    public Payment updateTotalValueAndType() {
        pagamento.setValorTotal(exames.stream().map(Exam::getValorExame).reduce(0d,(exam1, exam2) ->exam1+exam2));
        pagamento.setTipoPagamento(getPagamento().getTipoPagamento());
        return pagamento;
    }

    public void removeExamFromList(Exam exam) {
        exames.remove(exam);
    }
}
