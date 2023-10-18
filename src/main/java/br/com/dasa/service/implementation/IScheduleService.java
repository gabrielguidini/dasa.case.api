package br.com.dasa.service.implementation;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ScheduleRepository;
import br.com.dasa.service.ExamService;
import br.com.dasa.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Component
public class IScheduleService implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ExamService examService;

    @Override
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return scheduleRepository.findAll().isEmpty() ? ResponseEntity.notFound().build()
                :ResponseEntity.ok(scheduleRepository.findAll());
    }

    @Override
    public ResponseEntity<Optional<Schedule>> getById(Long id_agendamento){
        return scheduleRepository.findById(id_agendamento).isEmpty() ? ResponseEntity.notFound().build()
                :ResponseEntity.ok(scheduleRepository.findById(id_agendamento));
    }

    @Override
    public ResponseEntity<Schedule> createSchedule(ScheduleDTO schedule, UriComponentsBuilder uriBuilder){
        var uri =uriBuilder.path("/dasa").buildAndExpand(scheduleRepository.save(new Schedule(schedule))).toUri();
        return ResponseEntity.created(uri).body(new Schedule(schedule));
    }

    @Override
    public void updateValueAndType(Schedule schedule){
        schedule.setPagamento(schedule.updateTotalValueAndType());
    }

    @Override
    public ResponseEntity deleteExam(Long idAgendamento, ExamDTO exame, UriComponentsBuilder uriBuilder){
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
