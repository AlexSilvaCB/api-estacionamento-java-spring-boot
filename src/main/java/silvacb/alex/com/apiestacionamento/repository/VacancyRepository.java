package silvacb.alex.com.apiestacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silvacb.alex.com.apiestacionamento.entity.Vacancy;

import java.util.Optional;

public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    Optional<Vacancy> findByCode(String code);

    Optional<Vacancy> findFirstByStatus(Vacancy.StatusVacancy statusVacancy);

    long countByStatus(Vacancy.StatusVacancy statusVacancy);

}