package geek.persist.repo;

import geek.persist.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByLogin(String login);
//
//    boolean existsUserByEmail(String email);
//
//    Optional<User> findUserByEmail (String email);
}
