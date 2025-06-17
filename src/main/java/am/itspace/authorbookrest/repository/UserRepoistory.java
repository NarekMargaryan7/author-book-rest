package am.itspace.authorbookrest.repository;

import am.itspace.authorbookrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepoistory extends JpaRepository<User, Integer>{
    Optional<User> findByEmail(String email);
}
