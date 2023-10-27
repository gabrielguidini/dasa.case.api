package br.com.dasa.arrange;

import br.com.dasa.model.Schedule;

public class ScheduleArrange {

    private ScheduleArrange(){
    }

    public static Schedule getValidSchedule(){
        return Schedule.builder()
                .idAgendamento(1L)
                .exames(ExamArrange.getValidExams())
                .pagamento(PaymentArrange.getValidPayment())
                .build();
    }
}
