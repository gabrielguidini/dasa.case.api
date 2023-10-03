package br.com.dasa.repository;

import br.com.dasa.model.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//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

@Repository
public interface ExamRepository extends JpaRepository<Exam,Long> {
}
