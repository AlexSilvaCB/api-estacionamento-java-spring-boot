package silvacb.alex.com.apiestacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import silvacb.alex.com.apiestacionamento.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
