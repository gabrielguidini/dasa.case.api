package br.com.dasa.service;

import br.com.dasa.dto.ExamDTO;
import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Schedule;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@Service
public interface ScheduleService {
    ResponseEntity<List<Schedule>> getAllSchedules();
    ResponseEntity<Optional<Schedule>> getById(Long id_agendamento);
    ResponseEntity<Schedule> createSchedule(ScheduleDTO schedule,UriComponentsBuilder uriBuilder);
    void updateValueAndType(Schedule schedule);
    ResponseEntity deleteExam(Long id_agendamento,ExamDTO exame, UriComponentsBuilder uriBuilder);
}
