package com.projects.service;

import com.projects.exception.NotFoundException;
import com.projects.models.Book;
import com.projects.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<Book> searchBooks(String keyword) {
        if(keyword != null) {
            bookRepository.search(keyword);
        }
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Book findBookById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Book with id %d not found", id)));
    }

    public void createBook(Book book) {
        bookRepository.save(book);
    }

    public void updateBook(Book book) {
        bookRepository.save(book);
    }

    public void deleteBookById(Long id) {
        boolean exists = bookRepository.existsById(id);
        if(!exists) {
            throw new NotFoundException(String.format("Book with id %d not found"));
        }
        bookRepository.deleteById(id);
    }

}
