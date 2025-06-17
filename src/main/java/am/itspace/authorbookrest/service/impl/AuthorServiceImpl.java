package am.itspace.authorbookrest.service.impl;


import am.itspace.authorbookrest.dto.AuthorDto;
import am.itspace.authorbookrest.dto.AuthorResponseDto;
import am.itspace.authorbookrest.dto.SaveAuthorRequest;
import am.itspace.authorbookrest.entity.Author;
import am.itspace.authorbookrest.exception.AuthorNotFoundException;
import am.itspace.authorbookrest.mapper.AuthorMapper;
import am.itspace.authorbookrest.repository.AuthorRepository;
import am.itspace.authorbookrest.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Primary
public class AuthorServiceImpl implements AuthorService {


    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    @Override
    public List<AuthorResponseDto> findAll() {
        List<Author> authors = authorRepository.findAll();
        return authorMapper.toDtoList(authors);
    }

    @Override
    public Page<Author> findAll(Pageable pageable) {
        return authorRepository.findAll(pageable);
    }

    @Override
    public AuthorResponseDto save(SaveAuthorRequest authorRequest) {
        Author author = authorRepository.save(authorMapper.toEntity(authorRequest));

        return authorMapper.toDto(author);
    }

    @Override
    public void deleteById(int id) {
        if (!authorRepository.existsById(id)) {
            throw new AuthorNotFoundException("Author not found with " + id + " id");
        }
        authorRepository.deleteById(id);
    }

    @Override
    public AuthorResponseDto findById(int id) {
        Author author = authorRepository
                .findById(id)
                .orElse(null);
        if (author == null) {
            return null;
        }
        return authorMapper.toDto(author);
    }

    @Override
    public Optional<Author> findByPhone(String phone) {
        return authorRepository.findByPhone(phone);
    }
}
