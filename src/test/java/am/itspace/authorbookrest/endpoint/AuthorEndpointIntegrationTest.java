package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.config.TestSecurityConfig;
import am.itspace.authorbookrest.dto.SaveAuthorRequest;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AuthorEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String PHONE = "999999";

    @BeforeEach
    void cleanUp() {
        authorRepository.findByPhone(PHONE).ifPresent(author -> authorRepository.deleteById(author.getId()));
    }

    @Test
    @Order(1)
    void createAuthor_shouldReturn200() throws Exception {
        SaveAuthorRequest request = new SaveAuthorRequest();
        request.setName("Test");
        request.setSurname("Author");
        request.setPhone(PHONE);
        request.setGender(am.itspace.authorbookrest.entity.Gender.MALE);
        request.setDateOfBirthday(new Date());

        mockMvc.perform(post("/authors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test"));
    }

    @Test
    @Order(2)
    void getAllAuthors_shouldReturnList() throws Exception {
        mockMvc.perform(get("/authors"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    @Order(3)
    void deleteAuthor_shouldReturnOk() throws Exception {
        Author author = new Author();
        author.setName("ToDelete");
        author.setSurname("Test");
        author.setPhone("del-123");
        author.setGender(am.itspace.authorbookrest.entity.Gender.FEMALE);
        author.setDateOfBirthday(new Date());

        Author saved = authorRepository.save(author);

        mockMvc.perform(delete("/authors/" + saved.getId()))
                .andExpect(status().isOk());

        assertThat(authorRepository.findById(saved.getId())).isEmpty();
    }
}
