package com.projects.controller;

import com.projects.models.Book;
import com.projects.service.AuthorService;
import com.projects.service.BookService;
import com.projects.service.CategoryService;
import com.projects.service.PublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final CategoryService categoryService;
    private final PublisherService publisherService;

    @RequestMapping("/books")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findAllBooks(Model model) {
        final List<Book> books = bookService.findAllBooks();

        model.addAttribute("books", books);
        return "list-books";
    }

    @RequestMapping("/searchBook")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String searchBook(@Param("keyword") String keyword, Model model) {
        final List<Book> books = bookService.searchBooks(keyword);

        model.addAttribute("books", books);
        model.addAttribute("keyword", keyword);
        return "list-books";
    }

    @RequestMapping("/book/{id}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findBookById(@PathVariable("id") Long id, Model model) {
        final Book book = bookService.findBookById(id);

        model.addAttribute("book", book);
        return "list-book";
    }

    @GetMapping("/add")
    @PreAuthorize("hasAuthority('library:write')")
    public String showCreateForm(Book book, Model model) {
        model.addAttribute("categories", categoryService.findAllCategories());
        model.addAttribute("authors", authorService.findAllAuthors());
        model.addAttribute("publishers", publisherService.findAllPublishers());
        return "add-book";
    }

    @RequestMapping("/add-book")
    @PreAuthorize("hasAuthority('library:write')")
    public String createBook(Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-book";
        }

        bookService.createBook(book);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    @GetMapping("/update/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final Book book = bookService.findBookById(id);

        model.addAttribute("book", book);
        return "update-book";
    }

    @RequestMapping("/update-book/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String updateBook(@PathVariable("id") Long id, Book book, BindingResult result, Model model) {
        if (result.hasErrors()) {
            book.setId(id);
            return "update-book";
        }

        bookService.updateBook(book);
        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

    @RequestMapping("/remove-book/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String deleteBook(@PathVariable("id") Long id, Model model) {
        bookService.deleteBookById(id);

        model.addAttribute("book", bookService.findAllBooks());
        return "redirect:/books";
    }

}
