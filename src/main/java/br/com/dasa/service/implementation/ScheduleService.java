package br.com.dasa.service.implementation;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService{
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ExamService examService;

    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return scheduleRepository.findAll().isEmpty() ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(scheduleRepository.findAll());
    }

    public ResponseEntity<Optional<Schedule>> getById(Long id_agendamento){
        return scheduleRepository.findById(id_agendamento).isEmpty() ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(scheduleRepository.findById(id_agendamento));
    }
    public ResponseEntity<Schedule> createSchedule(ScheduleDTO schedule, UriComponentsBuilder uriBuilder){
        var uri = uriBuilder.path("/dasa").buildAndExpand(scheduleRepository.save(new Schedule(schedule))).toUri();
        return ResponseEntity.created(uri).body(new Schedule(schedule));
    }

    public void updateValueAndType(Schedule schedule){
        schedule.getPagamento().setValorTotal(schedule.getExames().stream().map(Exam::getValorExame).reduce(0d, Double::sum));
        schedule.getPagamento().setTipoPagamento(schedule.getPagamento().getTipoPagamento());
    }

    public ResponseEntity<?> deleteExam(Long idAgendamento, ExamDTO exame, UriComponentsBuilder uriBuilder){
        var schedule = scheduleRepository.findById(idAgendamento).get();
        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand (scheduleRepository.findById(idAgendamento).get()).toUri();
        if(examService.getExamById(exame.getIdExame())){
            schedule.removeExamFromList(new Exam(exame));
            examService.deleteById(exame.getIdExame());
            updateValueAndType(schedule);
            return ResponseEntity.created(uri).body(scheduleRepository.findById(idAgendamento));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
