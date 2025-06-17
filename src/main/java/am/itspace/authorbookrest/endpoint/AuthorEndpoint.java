package am.itspace.authorbookrest.endpoint;


import am.itspace.authorbookrest.dto.AuthorDto;
import am.itspace.authorbookrest.dto.AuthorResponseDto;
import am.itspace.authorbookrest.dto.SaveAuthorRequest;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.repository.AuthorRepository;
import am.itspace.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthorEndpoint {
    private final AuthorService authorService;

    @GetMapping("/authors")
    public ResponseEntity<List<AuthorResponseDto>> getAll() {
        return ResponseEntity.ok(authorService.findAll());
    }

    @GetMapping("/authors/{id}")
    public ResponseEntity<AuthorResponseDto> getById(@PathVariable int id) {
        return ResponseEntity.ok(authorService.findById(id));
    }

    @PostMapping("/authors")
    public ResponseEntity<?> create(@RequestBody SaveAuthorRequest authorRequest) {
        if (authorService.findByPhone(authorRequest.getPhone()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();

        }
        AuthorResponseDto authorFromDb = authorService.save(authorRequest);

        return ResponseEntity.ok(authorFromDb);
    }


    @DeleteMapping("/authors/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (authorService.findById(id) == null) {
            return ResponseEntity.notFound().build();
        }
        authorService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}

