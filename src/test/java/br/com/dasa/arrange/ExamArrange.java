package br.com.dasa.arrange;

import br.com.dasa.model.Exam;
import br.com.dasa.model.enums.ExamEnum;

import java.util.ArrayList;

public class ExamArrange {
    private ExamArrange(){}

    public static Exam getValidExam(){
        return Exam.builder()
                .idExame(2L)
                .nomeExame("string")
                .tipoExame(ExamEnum.COLETA)
                .valorExame(50d)
                .build();
    }

    public static ArrayList getValidExams(){
        ArrayList<Exam> exams = new ArrayList<>();
        exams.add(Exam.builder()
                .idExame(1L)
                .nomeExame("string")
                .tipoExame(ExamEnum.IMAGEM)
                .valorExame(200d)
                .build());
        return exams;
    }
}
