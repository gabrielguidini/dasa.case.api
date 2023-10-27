package br.com.dasa.model;


import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "agendamento")
@Getter
@Setter
@Builder
@Embeddable
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAgendamento;
    @OneToMany(cascade = CascadeType.MERGE)
    @PrimaryKeyJoinColumn()
    private List<Exam> exames = new ArrayList<>();
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private Payment pagamento;

    @JsonCreator
    public Schedule(ScheduleDTO scheduleDTO){
        this.idAgendamento = scheduleDTO.getIdAgendamento();
        this.pagamento = scheduleDTO.getPagamento();
        exames.add(scheduleDTO.getExames().iterator().next());
        pagamento.setValorTotal(pagamento.getValorTotal());
    }

    public Schedule(){}

    public void addExame(Exam exame) {
        exames.add(exame);
    }

//    public Payment updateTotalValueAndType() {
//        pagamento.setValorTotal(exames.stream().map(Exam::getValorExame).reduce(0d, Double::sum));
//        pagamento.setTipoPagamento(getPagamento().getTipoPagamento());
//        return pagamento;
//    }

    public Schedule(Long idAgendamento, List<Exam> exames, Payment pagamento) {
        this.idAgendamento = idAgendamento;
        this.exames = exames;
        this.pagamento = pagamento;
    }

    public void removeExamFromList(Exam exam) {
        exames.remove(exam);
    }
}
