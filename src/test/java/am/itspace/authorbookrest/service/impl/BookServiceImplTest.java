package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.dto.BookDto;
import am.itspace.authorbookrest.dto.SaveBookRequest;
import am.itspace.authorbookrest.entity.Book;
import am.itspace.authorbookrest.mapper.BookMapper;
import am.itspace.authorbookrest.repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)

class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    void findAll_ShouldReturnDtoList() {
        List<Book> books = List.of(new Book(), new Book());
        List<BookDto> dtos = List.of(new BookDto(), new BookDto());

        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toDtoList(books)).thenReturn(dtos);

        List<BookDto> result = bookService.findAll();

        assertEquals(dtos, result);
    }

    @Test
    void save_ShouldReturnSavedDto() {
        SaveBookRequest request = new SaveBookRequest();
        Book entity = new Book();
        Book saved = new Book();
        BookDto dto = new BookDto();

        when(bookMapper.toEntity(request)).thenReturn(entity);
        when(bookRepository.save(entity)).thenReturn(saved);
        when(bookMapper.toDto(saved)).thenReturn(dto);

        BookDto result = bookService.save(request);

        assertEquals(dto, result);
    }

    @Test
    void findById_WhenFound_ShouldReturnDto() {
        int id = 1;
        Book book = new Book();
        BookDto dto = new BookDto();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(dto);

        BookDto result = bookService.findById(id);

        assertEquals(dto, result);
    }

    @Test
    void findById_WhenNotFound_ShouldThrowException() {
        when(bookRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookService.findById(1));
    }

    @Test
    void deleteById_WhenExists_ShouldDelete() {
        int id = 1;
        when(bookRepository.existsById(id)).thenReturn(true);

        bookService.deleteById(id);

        verify(bookRepository).deleteById(id);
    }

    @Test
    void deleteById_WhenNotExists_ShouldThrowException() {
        int id = 2;
        when(bookRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bookService.deleteById(id));
    }

    @Test
    void update_ShouldReturnUpdatedDto() {
        SaveBookRequest request = new SaveBookRequest();
        Book entity = new Book();
        Book saved = new Book();
        BookDto dto = new BookDto();

        when(bookMapper.toEntity(request)).thenReturn(entity);
        when(bookRepository.save(entity)).thenReturn(saved);
        when(bookMapper.toDto(saved)).thenReturn(dto);

        BookDto result = bookService.update(request);

        assertEquals(dto, result);
    }
}
