package silvacb.alex.com.apiestacionamento.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import silvacb.alex.com.apiestacionamento.entity.Client;
import silvacb.alex.com.apiestacionamento.repository.projection.ClientProjection;

public interface ClientRepository extends JpaRepository<Client, Long> {
    @Query("select c from Client c")
    Page<ClientProjection> findAllPageable(Pageable pageable);

   Client findByUsersId(Long id);

}
