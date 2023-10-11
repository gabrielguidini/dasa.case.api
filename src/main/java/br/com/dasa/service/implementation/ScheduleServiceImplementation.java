package br.com.dasa.service.implementation;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ScheduleRepository;
import br.com.dasa.service.ExamService;
import br.com.dasa.service.ScheduleService;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Component
public class ScheduleServiceImplementation implements ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ExamService examService;

    @Override
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return scheduleRepository.findAll().isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                :ResponseEntity.ok(scheduleRepository.findAll()) ;
    }

    @Override
    public ResponseEntity<Optional<Schedule>> getById(Long id_agendamento){
        return scheduleRepository.findById(id_agendamento).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
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
    public ResponseEntity deleteExam(Long id_agendamento,ExamDTO exame, UriComponentsBuilder uriBuilder){
        var schedule = scheduleRepository.findById(id_agendamento).get();
        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand (scheduleRepository.findById(id_agendamento).get()).toUri();
        if(examService.getExamById(exame.getIdExame())){
            schedule.deleteExame(exame);
            examService.deleteById(new Exam(exame).getIdExame());
            updateValueAndType(schedule);
            return ResponseEntity.created(uri).body(scheduleRepository.findById(id_agendamento));
        }
        else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
