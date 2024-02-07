package silvacb.alex.com.apiestacionamento.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silvacb.alex.com.apiestacionamento.entity.Client;
import silvacb.alex.com.apiestacionamento.exception.CpfUniqueViolationException;
import silvacb.alex.com.apiestacionamento.exception.EntityNotFoundException;
import silvacb.alex.com.apiestacionamento.repository.ClientRepository;
import silvacb.alex.com.apiestacionamento.repository.projection.ClientProjection;

@RequiredArgsConstructor
@Service
public class ClientService {

    private final ClientRepository cliRepository;

    @Transactional
    public Client save(Client client) {
        try {
            return cliRepository.save(client);
        } catch (DataIntegrityViolationException ex) {
            throw new CpfUniqueViolationException(client.getCpf());
        }
    }

    @Transactional(readOnly = true)
    public Client searchById(Long id){
            return cliRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException("Cliente", String.valueOf(id)));
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> searchAll(Pageable pageable) {
        return cliRepository.findAllPageable(pageable);
    }

    @Transactional(readOnly = true)
    public Client searchUserId(Long id) {
        return cliRepository.findByUsersId(id);
    }

    @Transactional(readOnly = true)
    public Client buscarPorCpf(String cpf) {
        return cliRepository.findByCpf(cpf).orElseThrow(
                () -> new EntityNotFoundException(String.format("Cliente", cpf))
        );
    }


}
