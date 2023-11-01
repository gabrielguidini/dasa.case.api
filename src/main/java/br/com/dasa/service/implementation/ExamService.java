package br.com.dasa.service.implementation;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    public ResponseEntity<?> addExam(Long idAgendamento,ExamDTO exame, UriComponentsBuilder uriBuilder) {
        var schedule = scheduleRepository.findById(idAgendamento).get();
        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand(scheduleRepository.findById(idAgendamento).get()).toUri();
        if (examRepository.existsById(exame.getIdExame())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            schedule.addExameIntoSchedule(new Exam(exame));
            saveExam(exame);
            schedule.getPagamento().setValorTotal(schedule.getExames().stream().map(Exam::getValorExame).reduce(0d, Double::sum ));
            return ResponseEntity.created(uri).body(scheduleRepository.findById(idAgendamento));
        }
    }

    public Boolean getExamById(Long id){
        return examRepository.findById(id).isPresent();
    }

    public void saveExam(ExamDTO exam){
        examRepository.save(new Exam(exam));
    }

    public void deleteById(Long idExame) {
        examRepository.deleteById(idExame);
    }
}
