package silvacb.alex.com.apiestacionamento.services;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silvacb.alex.com.apiestacionamento.entity.Client;
import silvacb.alex.com.apiestacionamento.exception.CpfUniqueViolationException;
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
            throw new CpfUniqueViolationException(
                    String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", client.getCpf()));
        }
    }

    @Transactional(readOnly = true)
    public Client searchById(Long id){
            return cliRepository.findById(id).orElseThrow(
                    ()-> new EntityNotFoundException(String.format("Cliente id= %s não encontrado.", id)));
    }

    @Transactional(readOnly = true)
    public Page<ClientProjection> searchAll(Pageable pageable) {
        return cliRepository.findAllPageable(pageable);
    }

}