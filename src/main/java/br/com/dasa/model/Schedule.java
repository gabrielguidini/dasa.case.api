package br.com.dasa.model;


import br.com.dasa.dto.ScheduleDTO;
import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "agendamento")
@Data
@Getter
@Setter
@Embeddable
public class Schedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_agendamento;
    @OneToMany(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private List<Exam> exames;
    @OneToOne(cascade = CascadeType.ALL)
    @PrimaryKeyJoinColumn()
    private Payment pagamento;

    @JsonCreator
    public Schedule(ScheduleDTO scheduleDTO){
        this.id_agendamento = scheduleDTO.id_agendamento();
        this.pagamento=scheduleDTO.pagamento();
        exames = new ArrayList<>();
        exames.add(scheduleDTO.exames());
    }

    public Schedule(){}
    
    
}
