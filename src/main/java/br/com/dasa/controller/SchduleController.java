package br.com.dasa.controller;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        return ResponseEntity.ok(scheduleRepository.findAll());
    }

    @GetMapping("/{id_agendamento}")
    public ResponseEntity<Optional<Schedule>> getById(@PathVariable @Valid Long id_agendamento){
        return ResponseEntity.ok(scheduleRepository.findById(id_agendamento));
    }

    @PostMapping("/{agendamento}")
    @Transactional
    public ResponseEntity<List<Schedule>> createSchedule(@RequestBody @Valid ScheduleDTO schedule){
        scheduleRepository.save(new Schedule(schedule));
        return ResponseEntity.ok(scheduleRepository.findAll());
    }

    @PutMapping("/{id_agendamento}")
    @Transactional
    public ResponseEntity<Optional<Schedule>> addExam(@PathVariable @RequestParam Long id_agendamento,@RequestBody ExamDTO exame){
        var schedule = scheduleRepository.findById(id_agendamento).get();
        schedule.addExame(addIntoExamList(exame));
        schedule.setPagamento(schedule.updateTotalValue());
        return ResponseEntity.ok(scheduleRepository.findById(id_agendamento));
    }

    public ExamDTO addIntoExamList(@RequestBody @Valid ExamDTO exam){
        examRepository.save(new Exam(exam));
        return exam;
    }
}
