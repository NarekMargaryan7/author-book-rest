package am.itspace.authorbookrest.service.impl;

import am.itspace.authorbookrest.dto.AuthorResponseDto;
import am.itspace.authorbookrest.dto.SaveAuthorRequest;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.exception.AuthorNotFoundException;
import am.itspace.authorbookrest.mapper.AuthorMapper;
import am.itspace.authorbookrest.repository.AuthorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

        @Mock
        private AuthorRepository authorRepository;

        @Mock
        private AuthorMapper authorMapper;

        @InjectMocks
        private AuthorServiceImpl authorService;

        @Test
        void findAll_ShouldReturnDtoList() {
            List<Author> authors = List.of(new Author(), new Author());
            List<AuthorResponseDto> dtos = List.of(new AuthorResponseDto(), new AuthorResponseDto());

            when(authorRepository.findAll()).thenReturn(authors);
            when(authorMapper.toDtoList(authors)).thenReturn(dtos);

            List<AuthorResponseDto> result = authorService.findAll();

            assertEquals(dtos, result);
        }

        @Test
        void findAllPageable_ShouldReturnPage() {
            List<Author> authors = List.of(new Author(), new Author());
            Page<Author> page = new PageImpl<>(authors);
            PageRequest pageable = PageRequest.of(0, 2);

            when(authorRepository.findAll(pageable)).thenReturn(page);

            Page<Author> result = authorService.findAll(pageable);

            assertEquals(2, result.getContent().size());
        }

        @Test
        void save_ShouldMapAndSaveAuthor() {
            SaveAuthorRequest request = new SaveAuthorRequest();
            Author author = new Author();
            Author saved = new Author();
            AuthorResponseDto dto = new AuthorResponseDto();

            when(authorMapper.toEntity(request)).thenReturn(author);
            when(authorRepository.save(author)).thenReturn(saved);
            when(authorMapper.toDto(saved)).thenReturn(dto);

            AuthorResponseDto result = authorService.save(request);

            assertEquals(dto, result);
        }

        @Test
        void deleteById_WhenExists_ShouldDelete() {
            int id = 1;
            when(authorRepository.existsById(id)).thenReturn(true);

            authorService.deleteById(id);

            verify(authorRepository).deleteById(id);
        }

        @Test
        void deleteById_WhenNotExists_ShouldThrowException() {
            int id = 99;
            when(authorRepository.existsById(id)).thenReturn(false);

            assertThrows(AuthorNotFoundException.class, () -> authorService.deleteById(id));
        }

        @Test
        void findById_WhenExists_ShouldReturnDto() {
            int id = 1;
            Author author = new Author();
            AuthorResponseDto dto = new AuthorResponseDto();

            when(authorRepository.findById(id)).thenReturn(Optional.of(author));
            when(authorMapper.toDto(author)).thenReturn(dto);

            AuthorResponseDto result = authorService.findById(id);

            assertEquals(dto, result);
        }

        @Test
        void findById_WhenNotExists_ShouldReturnNull() {
            int id = 99;
            when(authorRepository.findById(id)).thenReturn(Optional.empty());

            assertNull(authorService.findById(id));
        }

        @Test
        void findByPhone_ShouldReturnOptional() {
            String phone = "123456789";
            Author author = new Author();

            when(authorRepository.findByPhone(phone)).thenReturn(Optional.of(author));

            Optional<Author> result = authorService.findByPhone(phone);

            assertTrue(result.isPresent());
            assertEquals(author, result.get());
        }
    }

