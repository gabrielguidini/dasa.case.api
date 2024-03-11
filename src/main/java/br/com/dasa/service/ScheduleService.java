package br.com.dasa.service;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ScheduleRepository;
import br.com.dasa.utils.ScheduleUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class ScheduleService{
    private final ScheduleRepository scheduleRepository;
    private final ExamService examService;

    public ScheduleService(ScheduleRepository scheduleRepository, ExamService examService) {
        this.scheduleRepository = scheduleRepository;
        this.examService = examService;
    }

    public ResponseEntity<ScheduleDTO> createSchedule(Schedule schedule, UriComponentsBuilder uriBuilder) throws Exception {
        UUID scheduleId = UUID.randomUUID();

        try{

            if (scheduleRepository.existsById(schedule.getIdAgendamento())) {
                log.error("Error ScheduleService.create() -> already exists");
                return ResponseEntity.badRequest().build();
            }

            schedule.setIdAgendamento(scheduleId);

            saveSchedule(schedule);

            updateValueAndType(schedule);

            var uri = uriBuilder.path("/dasa").buildAndExpand(scheduleRepository.save(schedule)).toUri();

            return ResponseEntity.created(uri).body(ScheduleUtils.convertScheduleToDto(schedule));

        } catch (Exception e) {
            log.error("Error ScheduleService.createSchedule() -> schduelEvent {}" , e.getMessage());
            throw new Exception("ScheduleService.createSchedule() -> Not possible save schedule due to {}" + e.getMessage());
        }
    }

    public ResponseEntity<List<Schedule>> getAllSchedules(){
        return scheduleRepository.findAll().isEmpty() ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(scheduleRepository.findAll());
    }

    public ResponseEntity<Optional<Schedule>> getById(UUID scheduleId){
        return scheduleRepository.findByUuid(scheduleId).isEmpty() ?
                ResponseEntity.notFound().build() : ResponseEntity.ok(scheduleRepository.findById(scheduleId));
    }

    public void updateValueAndType(Schedule schedule){
        schedule.getPagamento().setValorTotal(schedule.getExames().stream().map(Exam::getValorExame).reduce(0d, Double::sum));
        schedule.getPagamento().setTipoPagamento(schedule.getPagamento().getTipoPagamento());
    }

    public ResponseEntity<?> deleteExam(UUID scheduleId, ExamDTO exame, UriComponentsBuilder uriBuilder){
        var schedule = scheduleRepository.findById(scheduleId).get();
        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand (scheduleRepository.findById(scheduleId).get()).toUri();
        if(examService.getExamById(exame.getIdExame())){
            schedule.removeExamFromList(new Exam(exame));
            examService.deleteById(exame.getIdExame());
            updateValueAndType(schedule);
            return ResponseEntity.created(uri).body(scheduleRepository.findById(scheduleId));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    public void saveSchedule(Schedule schedule) {
        scheduleRepository.save(schedule);
        ScheduleUtils.convertScheduleToDto(schedule);
    }
}
