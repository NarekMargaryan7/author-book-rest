package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.config.TestSecurityConfig;
import am.itspace.authorbookrest.dto.BookDto;
import am.itspace.authorbookrest.dto.SaveBookRequest;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.entity.Gender;
import am.itspace.authorbookrest.repository.AuthorRepository;
import am.itspace.authorbookrest.repository.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Import(TestSecurityConfig.class)
@TestPropertySource(locations = "classpath:application.yml")
@Transactional
class BookEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private BookRepository bookRepository;



    @BeforeEach
    void setUp() {
        bookRepository.deleteAll();
        authorRepository.deleteAll();

        Author author = Author.builder()
                .name("Naro")
                .surname("Margaryans")
                .phone("123456")
                .gender(Gender.MALE)
                .dateOfBirthday(new GregorianCalendar(1980, Calendar.JANUARY, 1).getTime())
                .build();

        authorRepository.save(author);
    }

    @Test
    void createBook_thenGetAllBooks_thenDeleteBook() throws Exception {
        Author savedAuthor = authorRepository.save(Author.builder()
                .name("Narek")
                .surname("Margaryan")
                .gender(Gender.MALE)
                .phone("000000")
                .dateOfBirthday(new GregorianCalendar(2000, Calendar.JANUARY, 1).getTime())
                .build());

        SaveBookRequest request = SaveBookRequest.builder()
                .title("Integration Test Book")
                .price(25.5)
                .qty(5)
                .createdAt(new Date())
                .author(savedAuthor)
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        MvcResult result;
        result = mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.title").value("Integration Test Book"))
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        BookDto createdBook = objectMapper.readValue(responseBody, BookDto.class);
        int bookId = createdBook.getId();

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1));

        mockMvc.perform(delete("/books/{id}", bookId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/{id}", bookId))
                .andExpect(status().isNotFound());

    }
}
