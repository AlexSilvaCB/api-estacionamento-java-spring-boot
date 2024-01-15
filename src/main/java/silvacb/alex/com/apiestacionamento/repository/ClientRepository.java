package silvacb.alex.com.apiestacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import silvacb.alex.com.apiestacionamento.entity.Client;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
