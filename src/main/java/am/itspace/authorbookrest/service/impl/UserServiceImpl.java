package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.entity.User;
import am.itspace.authorbookrest.repository.UserRepoistory;
import am.itspace.authorbookrest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepoistory userRepoistory;

    @Override
   public User save (User user) {
        return userRepoistory.save(user);
    }
    @Override
    public Optional<User> findByEmail (String email) {
        return userRepoistory.findByEmail(email);
    }

    @Override
    public User findById(int id) {
        return userRepoistory.findById(id).get();
    }
}
