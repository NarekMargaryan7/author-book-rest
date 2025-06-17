package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.entity.User;
import am.itspace.authorbookrest.repository.UserRepoistory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepoistory userRepoistory;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void save_ShouldReturnSavedUser() {
        User user = new User();
        when(userRepoistory.save(user)).thenReturn(user);

        User result = userService.save(user);

        assertEquals(user, result);
    }

    @Test
    void findByEmail_WhenFound_ShouldReturnUser() {
        String email = "test@mail.com";
        User user = new User();

        when(userRepoistory.findByEmail(email)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(user, result.get());
    }

    @Test
    void findByEmail_WhenNotFound_ShouldReturnEmpty() {
        String email = "notfound@mail.com";

        when(userRepoistory.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = userService.findByEmail(email);

        assertTrue(result.isEmpty());
    }

    @Test
    void findById_ShouldReturnUser() {
        int id = 1;
        User user = new User();
        when(userRepoistory.findById(id)).thenReturn(Optional.of(user));

        User result = userService.findById(id);

        assertEquals(user, result);
    }
}