package br.com.dasa.controller;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dasa")
public class SchduleController {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private ExamRepository examRepository;

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return scheduleRepository.findAll().isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                :ResponseEntity.ok(scheduleRepository.findAll()) ;
    }

    @GetMapping("/{id_agendamento}")
    public ResponseEntity<Optional<Schedule>> getById(@PathVariable @Valid Long id_agendamento){
        return scheduleRepository.findById(id_agendamento).isEmpty() ? new ResponseEntity<>(HttpStatus.NOT_FOUND)
                :ResponseEntity.ok(scheduleRepository.findById(id_agendamento));
    }

    @PostMapping()
    @Transactional
    public ResponseEntity<Schedule> createSchedule(@RequestBody @Valid ScheduleDTO schedule, UriComponentsBuilder uriBuilder){
        var uri =uriBuilder.path("/dasa").buildAndExpand(scheduleRepository.save(new Schedule(schedule))).toUri();
        return ResponseEntity.created(uri).body(new Schedule(schedule));
    }

    @PutMapping("/{id_agendamento}")
    @Transactional
    public ResponseEntity addExam(@PathVariable @RequestParam Long id_agendamento,@RequestBody ExamDTO exame, UriComponentsBuilder uriBuilder){
        var schedule = scheduleRepository.findById(id_agendamento).get();
        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand (scheduleRepository.findById(id_agendamento).get()).toUri();
        if(examRepository.existsById(exame.id_exame())){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }else{
            schedule.addExame(addIntoExamList(exame));
            updateValue(schedule);
            return ResponseEntity.created(uri).body(scheduleRepository.findById(id_agendamento));
        }
    }

    @DeleteMapping("/{id_agendamento}")
    @Transactional
    public ResponseEntity deleteExam(@PathVariable @RequestParam Long id_agendamento,@RequestBody ExamDTO exame, UriComponentsBuilder uriBuilder){
        var schedule = scheduleRepository.findById(id_agendamento).get();
        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand (scheduleRepository.findById(id_agendamento).get()).toUri();
        if(examRepository.existsById(exame.id_exame())){
            schedule.deleteExame(exame);
            examRepository.deleteById(new Exam(exame).getId_exame());
            updateValue(schedule);
            return ResponseEntity.created(uri).body(scheduleRepository.findById(id_agendamento));
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    private ExamDTO addIntoExamList(@RequestBody @Valid ExamDTO exam){
        examRepository.save(new Exam(exam));
        return exam;
    }

    private void updateValue(Schedule schedule){
        schedule.setPagamento(schedule.updateTotalValue());
    }
}
