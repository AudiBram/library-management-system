package com.projects.service;

import com.projects.exception.NotFoundException;
import com.projects.models.Author;
import com.projects.repository.AuthorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<Author> findAllAuthors() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Author findAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Author %d not found", id)));
    }

    public void createAuthor(Author author) {
        authorRepository.save(author);
    }

    public void updateAuthor(Author author) {
        authorRepository.save(author);
    }

    public void deleteAuthorById(Long id) {
        boolean exists = authorRepository.existsById(id);
        if(!exists) {
            throw new NotFoundException(String.format("Author %d not found, id"));
        }
        authorRepository.deleteById(id);;
    }

}
