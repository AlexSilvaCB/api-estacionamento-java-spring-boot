package silvacb.alex.com.apiestacionamento.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silvacb.alex.com.apiestacionamento.entity.ClientVacancy;
import silvacb.alex.com.apiestacionamento.exception.ReciboCheckInNotFoundException;
import silvacb.alex.com.apiestacionamento.repository.ClientVacancyRepository;
import silvacb.alex.com.apiestacionamento.repository.projection.ClientVacancyProjection;

@RequiredArgsConstructor
@Service
public class ClientVacancyService {

    private final ClientVacancyRepository repository;

    @Transactional
    public ClientVacancy salvar(ClientVacancy clientVacancy) {
        return repository.save(clientVacancy);
    }

    @Transactional(readOnly = true)
    public ClientVacancy buscarPorRecibo(String recibo) {
        return repository.findByReceiptAndDepartureDataIsNull(recibo).orElseThrow(
                () -> new ReciboCheckInNotFoundException(recibo)
        );
    }

    @Transactional(readOnly = true)
    public long getTotalDeVezesEstacionamentoCompleto(String cpf) {
        return repository.countByClientCpfAndDepartureDataIsNotNull(cpf);
    }

    @Transactional(readOnly = true)
    public Page<ClientVacancyProjection> buscarTodosPorClienteCpf(String cpf, Pageable pageable) {
        return repository.findAllByClientCpf(cpf, pageable);
    }

    @Transactional(readOnly = true)
    public Page<ClientVacancyProjection> buscarTodosPorUsuarioId(Long id, Pageable pageable) {
        return repository.findAllByClientUsersId(id, pageable);
    }

}
