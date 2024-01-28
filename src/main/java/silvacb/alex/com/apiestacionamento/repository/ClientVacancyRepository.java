package silvacb.alex.com.apiestacionamento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import silvacb.alex.com.apiestacionamento.entity.ClientVacancy;
import silvacb.alex.com.apiestacionamento.repository.projection.ClientVacancyProjection;

import java.util.Optional;

public interface ClientVacancyRepository extends JpaRepository<ClientVacancy, Long> {

    Optional<ClientVacancy> findByReceiptAndDepartureDataIsNull(String receipt);

    long countByClientCpfAndDepartureDataIsNotNull(String cpf);

    Page<ClientVacancyProjection> findAllByClientCpf(String cpf, Pageable pageable);

    Page<ClientVacancyProjection> findAllByClientUsersId(Long id, Pageable pageable);


}
