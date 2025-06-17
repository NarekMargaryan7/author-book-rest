package am.itspace.authorbookrest.mapper;

import am.itspace.authorbookrest.dto.AuthorResponseDto;
import am.itspace.authorbookrest.dto.BookDto;
import am.itspace.authorbookrest.dto.SaveBookRequest;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.entity.Book;
import am.itspace.authorbookrest.exception.AuthorNotFoundException;
import am.itspace.authorbookrest.repository.AuthorRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")

public interface BookMapper {


    BookDto toDto(Book book);

    List<BookDto> toDtoList(List<Book> books);


    Book toEntity(SaveBookRequest bookRequest);
}


