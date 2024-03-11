package br.com.dasa.controller;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Schedule;
import br.com.dasa.service.ExamService;
import br.com.dasa.service.ScheduleService;
import br.com.dasa.utils.ScheduleUtils;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/dasa")
public class ScheduleController {
    private final ScheduleService scheduleService;
    private final ExamService examService;

    //field injection
    public ScheduleController(ScheduleService scheduleService, ExamService examService) {
        this.scheduleService = scheduleService;
        this.examService = examService;
    }

    @GetMapping
    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return scheduleService.getAllSchedules();
    }

    @GetMapping("/{scheduleId}")
    public ResponseEntity<Optional<Schedule>> getById(@PathVariable @Valid UUID scheduleId){
        return scheduleService.getById(scheduleId);
    }

    @PostMapping
    @Transactional
    public ResponseEntity<ScheduleDTO> createSchedule(@RequestBody @Valid ScheduleDTO schedule, @NotNull UriComponentsBuilder uriBuilder) throws Exception {
        return scheduleService.createSchedule(ScheduleUtils.convertDtoToEntity(schedule),uriBuilder);
    }

    @PutMapping("/{scheduleId}")
    @Transactional
    public ResponseEntity<?> addExam(@PathVariable @RequestParam UUID scheduleId, @RequestBody ExamDTO exam, UriComponentsBuilder uriBuilder){
        return examService.addExam(scheduleId,exam,uriBuilder);
    }

    @DeleteMapping("/{scheduleId}")
    @Transactional
    public ResponseEntity<?> deleteExam(@PathVariable @RequestParam UUID scheduleId,@RequestBody ExamDTO exam, UriComponentsBuilder uriBuilder){
        return scheduleService.deleteExam(scheduleId,exam,uriBuilder);
    }
}
