package am.itspace.authorbookrest.mapper;

import am.itspace.authorbookrest.dto.AuthorDto;
import am.itspace.authorbookrest.dto.AuthorResponseDto;
import am.itspace.authorbookrest.dto.SaveAuthorRequest;
import am.itspace.authorbookrest.entity.Author;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    AuthorResponseDto toDto(Author author);


    List<AuthorResponseDto> toDtoList(List<Author> authors);

    Author toEntity(SaveAuthorRequest authorRequest);
}
