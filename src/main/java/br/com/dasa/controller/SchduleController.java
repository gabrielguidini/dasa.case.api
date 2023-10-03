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

    @PostMapping
    @Transactional
    public ResponseEntity<List<Exam>> createExam(@RequestBody @Valid ExamDTO exam){
        examRepository.save(new Exam(exam));
        return ResponseEntity.ok(examRepository.findAll());
    }

    @PostMapping("/dado")
    @Transactional
    public ResponseEntity<List<Schedule>> createSchedule(@RequestBody @Valid ScheduleDTO schedule){
        scheduleRepository.save(new Schedule(schedule));
        return ResponseEntity.ok(scheduleRepository.findAll());
    }

    @PutMapping(name = "addInfo")
    @Transactional
    public ResponseEntity<Optional<Schedule>> addExam(@RequestBody @Valid ExamDTO exame){
        var examSchedule  = examRepository.getReferenceById(exame.id_agendamento().longValue());
        exame.addExame(examSchedule);
        return ResponseEntity.ok(scheduleRepository.findById(schedule.id_agendamento().longValue()));
    }
}
