<h1>CASE DASA </h1>
``
mermaid
classDiagram
    class Schedule{
        - id_agendamento : Long
        - exames : Collection
        - pagamento : Payment
        + Schedule(scheduleDTO:ScheduleDTO)
        + Schedule()
        + addExam(exame : ExamDTO)
        + updateTotalValue():Payment
    }
    class Exam{
        - id_exames : Long
        - nome_exame : String
        - tipo_exame : ExamEnum
        - valor_exame : Double
        + Exam(examDTO : ExamDTO)
        + Exam
        + checkTypeValue(examEnum : ExamEnum) : Double
    }
    class Payment{
        - id_pagamento : Long
        - valor_total : Double
        - tipo_pagamento : PaymentEnum
        + Payment(paymentDTO : PaymentDTO)
        + Payment()
    }
    Schedule --> Exam
    Schedule --> Payment
```
