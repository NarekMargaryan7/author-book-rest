package am.itspace.authorbookrest.service;

import am.itspace.authorbookrest.entity.User;

import java.util.Optional;

public interface UserService {
    User save (User user);

    Optional<User> findByEmail(String username);

    User findById(int id);
}
