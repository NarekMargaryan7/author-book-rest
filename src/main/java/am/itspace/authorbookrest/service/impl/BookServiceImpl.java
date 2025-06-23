package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.dto.BookDto;
import am.itspace.authorbookrest.dto.SaveBookRequest;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.entity.Book;
import am.itspace.authorbookrest.exception.BookNotFoundException;
import am.itspace.authorbookrest.mapper.AuthorMapper;
import am.itspace.authorbookrest.mapper.BookMapper;
import am.itspace.authorbookrest.repository.AuthorRepository;
import am.itspace.authorbookrest.repository.BookRepository;
import am.itspace.authorbookrest.service.BookService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public  class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final AuthorRepository authorRepository;

    @Override
    public List<BookDto> findAll() {
        List<Book> books = bookRepository.findAll();
        return bookMapper.toDtoList(books);
    }


    @Override
    public BookDto save(SaveBookRequest bookRequest) {
        Author author = authorRepository.findById(bookRequest.getAuthor().getId())
                .orElseThrow(() -> new RuntimeException("Author not found"));

        Book book = bookMapper.toEntity(bookRequest);

        book.setAuthor(author);

        book = bookRepository.save(book);

        return bookMapper.toDto(book);
    }

    @Override
    public BookDto findById(int id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException("Book with id " + id + " not found"));
        return bookMapper.toDto(book);
    }

    @Override
    public void deleteById(int id) {
        if(!bookRepository.existsById(id)) {
            throw new EntityNotFoundException("Book with id " + id + " not found");
        }
        bookRepository.deleteById(id);
    }
    @Override
    public BookDto update(SaveBookRequest bookRequest) {
        Book book = bookRepository.save(bookMapper.toEntity(bookRequest));
        return bookMapper.toDto(book);
    }


}