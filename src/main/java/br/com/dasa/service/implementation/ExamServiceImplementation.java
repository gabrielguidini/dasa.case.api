package br.com.dasa.service.implementation;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.model.Exam;
import br.com.dasa.model.Schedule;
import br.com.dasa.repository.ExamRepository;
import br.com.dasa.repository.ScheduleRepository;
import br.com.dasa.service.ExamService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class ExamServiceImplementation implements ExamService {
    @Autowired
    private ExamRepository examRepository;
    @Autowired
    private ScheduleRepository scheduleRepository;

    @Override
    public ResponseEntity addExam(Long id_agendamento,ExamDTO exame, UriComponentsBuilder uriBuilder) {
        var schedule = scheduleRepository.findById(id_agendamento).get();
        var uri = uriBuilder.path("/dasa/{id_agendamento}").buildAndExpand(scheduleRepository.findById(id_agendamento).get()).toUri();
        if (examRepository.existsById(exame.getIdExame())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {
            schedule.addExame(addIntoExamList(exame));
            updateValue(schedule);
            return ResponseEntity.created(uri).body(scheduleRepository.findById(id_agendamento));
        }
    }

    @Override
    public Boolean getExamById(Long id){
        return examRepository.findById(id).isPresent();
    }

    @Override
    public ExamDTO addIntoExamList(@RequestBody @Valid ExamDTO exam){
        examRepository.save(new Exam(exam));
        return exam;
    }

    @Override
    public void updateValue(Schedule schedule){
        schedule.setPagamento(schedule.updateTotalValueAndType());
    }

    @Override
    public void deleteById(Long idExame) {
        examRepository.deleteById(idExame);
    }
}
