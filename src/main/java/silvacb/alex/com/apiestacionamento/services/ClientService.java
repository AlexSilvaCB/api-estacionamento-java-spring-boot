package silvacb.alex.com.apiestacionamento.services;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import silvacb.alex.com.apiestacionamento.entity.Client;
import silvacb.alex.com.apiestacionamento.exception.CpfUniqueViolationException;
import silvacb.alex.com.apiestacionamento.repository.ClientRepository;

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
                    String.format("CPF '%s' não pode ser cadastrado, já existe no sistema", client.getCpf())
            );
        }
    }
}
