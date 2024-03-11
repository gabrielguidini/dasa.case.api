package br.com.dasa.service;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.UUID;

@Component
public class ExamService {
    private final ExamRepository examRepository;
    private final ScheduleRepository scheduleRepository;

    public ExamService(ExamRepository examRepository, ScheduleRepository scheduleRepository) {
        this.examRepository = examRepository;
        this.scheduleRepository = scheduleRepository;
    }

    public ResponseEntity<?> addExam(UUID scheduleId, ExamDTO exame, UriComponentsBuilder uriBuilder) {
        var schedule = scheduleRepository.findById(scheduleId).get();

        UUID examId = UUID.randomUUID();

        exame.setIdExame(examId);

        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand(scheduleRepository.findById(scheduleId).get()).toUri();

        if (examRepository.existsById(exame.getIdExame())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            schedule.addExameIntoSchedule(new Exam(exame));
            saveExam(exame);
            schedule.getPagamento().setValorTotal(schedule.getExames().stream().map(Exam::getValorExame).reduce(0d, Double::sum ));
            return ResponseEntity.created(uri).body(scheduleRepository.findById(scheduleId));
        }
    }

    public Boolean getExamById(UUID examId){
        return examRepository.findById(examId).isPresent();
    }

    public void saveExam(ExamDTO exam){
        examRepository.save(new Exam(exam));
    }

    public void deleteById(UUID examId) {
        examRepository.deleteById(examId);
    }
}
