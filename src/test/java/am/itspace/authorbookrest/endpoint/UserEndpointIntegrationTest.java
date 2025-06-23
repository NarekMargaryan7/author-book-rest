package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.dto.SaveUserRequest;
import am.itspace.authorbookrest.dto.UserAuthRequest;
import am.itspace.authorbookrest.dto.UserAuthResponse;
import am.itspace.authorbookrest.entity.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void registerAndLoginUserTest() throws Exception {
        SaveUserRequest registerRequest = SaveUserRequest.builder()
                .name("Narek")
                .surname("Margaryan")
                .email("naro@mail.ru")
                .password("12345")
                .userType(UserType.USER)
                .build();

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(registerRequest)))
                .andExpect(status().isOk());

        // Логин
        UserAuthRequest loginRequest = new UserAuthRequest("naro@mail.ru", "12345");

        mockMvc.perform(post("/user/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.name").value("Narek"))
                .andExpect(jsonPath("$.surname").value("Margaryan"))
                .andExpect(jsonPath("$.userId").isNumber());
    }
    @Test
    void registerWithDuplicateEmail_shouldReturnConflict() throws Exception {
        SaveUserRequest request = SaveUserRequest.builder()
                .name("Narek")
                .surname("Margaryan")
                .email("nar@mail.ru")
                .password("12345")
                .userType(UserType.USER)
                .build();

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        mockMvc.perform(post("/user/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isConflict());
    }
}
