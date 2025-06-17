package am.itspace.authorbookrest.endpoint;

import am.itspace.authorbookrest.dto.BookDto;
import am.itspace.authorbookrest.dto.SaveBookRequest;
import am.itspace.authorbookrest.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class BookEndpoint {

    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookDto>> getAll() {
        return ResponseEntity.ok(bookService.findAll());

    }
    @GetMapping("/books/{id}")
    public ResponseEntity<BookDto> getBookById(@PathVariable int id) {
        return ResponseEntity.ok(bookService.findById(id));
    }

    @PostMapping("/books")
    public ResponseEntity<BookDto> create(@RequestBody @Valid SaveBookRequest saveBookRequest) {
        return ResponseEntity.ok(bookService.save(saveBookRequest));
    }
    @DeleteMapping("/books/{id}")
    public ResponseEntity<Void> delete(@PathVariable int id) {
        bookService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/books")
    public ResponseEntity<BookDto> updateBook(@RequestBody @Valid SaveBookRequest saveBookRequest) {
        return ResponseEntity.ok(bookService.update(saveBookRequest));
    }

}
