package br.com.dasa.service;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.model.Schedule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public interface ExamService {
    ResponseEntity addExam(Long id_agendamento, ExamDTO exame, UriComponentsBuilder uriBuilder);
    Boolean getExamById(Long id);
    ExamDTO addIntoExamList(ExamDTO exam);
    void updateValue(Schedule schedule);
    void deleteById(Long idExame);
}
