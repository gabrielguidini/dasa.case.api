package br.com.dasa.model;


import br.com.dasa.dto.ScheduleDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity(name = "agendamento")
@Getter
@Setter
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class Schedule {
    @Id @Builder.Default
    private UUID idAgendamento = UUID.randomUUID();;
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

    public void addExameIntoSchedule(Exam exame) {
        exames.add(exame);
    }

    public void removeExamFromList(Exam exam) {
        exames.remove(exam);
    }
}
