package com.projects.service;

import com.projects.LibManagementSystemApplication;
import com.projects.repository.AuthorRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.verify;

@SpringBootTest(classes = LibManagementSystemApplication.class)
@ExtendWith(MockitoExtension.class)
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorService authorService;

    @DisplayName("Find All Authors")
    @Test
    void testFindAllAuthor() {
        // when
        authorService.findAllAuthors();

        // then
        verify(authorRepository).findAll();
    }
}
