package br.com.dasa.utils;

import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Schedule;
import lombok.experimental.UtilityClass;

import static org.springframework.util.CollectionUtils.isEmpty;

@UtilityClass
public class ScheduleUtils {

    public ScheduleDTO convertScheduleToDto(Schedule schedule) {

        return ScheduleDTO.builder()
                .idAgendamento(schedule.getIdAgendamento())
                .exames(isEmpty(schedule.getExames()) ? null : schedule.getExames().stream().toList())
//                .exames(schedule.getExames().isEmpty() ? null : schedule.getExames().stream().toList())
                .pagamento(schedule.getPagamento())
                .build();

    }

    public Schedule convertDtoToEntity(ScheduleDTO schedule) {

        return Schedule.builder()
                .idAgendamento(schedule.getIdAgendamento())
                .exames(isEmpty(schedule.getExames()) ? null : schedule.getExames().stream().toList())
//                .exames(schedule.getExames().isEmpty() ? null : schedule.getExames().stream().toList())
                .pagamento(schedule.getPagamento())
                .build();

    }
}
