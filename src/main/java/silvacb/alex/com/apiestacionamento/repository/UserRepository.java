package silvacb.alex.com.apiestacionamento.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import silvacb.alex.com.apiestacionamento.entity.User;
import silvacb.alex.com.apiestacionamento.jwt.JwtUtils;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("select u.role from User u where u.username like :username")
    User.Role findRoleByUsername(String username);
}
