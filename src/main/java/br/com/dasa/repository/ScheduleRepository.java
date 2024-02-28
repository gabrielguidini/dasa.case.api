package br.com.dasa.repository;

import br.com.dasa.dto.ScheduleDTO;
import br.com.dasa.model.Schedule;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule,UUID> {

    @Query(value = "select a.idAgendamento, a.exames, a.pagamento from agendamento a where a.idAgendamento=:uuid")
    Optional<ScheduleDTO> findByUuid(@NotNull @Param("uuid") final UUID uuid);
}
