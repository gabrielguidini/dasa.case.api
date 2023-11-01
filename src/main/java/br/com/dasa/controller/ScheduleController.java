package br.com.dasa.controller;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Schedule;
import br.com.dasa.service.implementation.ExamService;
import br.com.dasa.service.implementation.ScheduleService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/dasa")
public class ScheduleController {
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private ExamService examService;

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{idAgendamento}")
    public ResponseEntity<Optional<Schedule>> getById(@PathVariable @Valid Long idAgendamento){
        return scheduleService.getById(idAgendamento);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Schedule> createSchedule(@RequestBody @Valid ScheduleDTO schedule, @NotNull UriComponentsBuilder uriBuilder){
        return scheduleService.createSchedule(schedule,uriBuilder);
    }

    @PutMapping("/{idAgendamento}")
    @Transactional
    public ResponseEntity<?> addExam(@PathVariable @RequestParam Long idAgendamento, @RequestBody ExamDTO exam, UriComponentsBuilder uriBuilder){
        return examService.addExam(idAgendamento,exam,uriBuilder);
    }

    @DeleteMapping("/{idAgendamento}")
    @Transactional
    public ResponseEntity<?> deleteExam(@PathVariable @RequestParam Long idAgendamento,@RequestBody ExamDTO exam, UriComponentsBuilder uriBuilder){
        return scheduleService.deleteExam(idAgendamento,exam,uriBuilder);
    }
}
