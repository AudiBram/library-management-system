package com.projects.controller;

import com.projects.models.Author;
import com.projects.service.AuthorService;
import lombok.RequiredArgsConstructor;
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
public class AuthorController {

    private final AuthorService authorService;

    @RequestMapping("/authors")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findAllAuthors(Model model) {
        final List<Author> authors = authorService.findAllAuthors();

        model.addAttribute("authors", authors);
        return "list-authors";
    }

    @RequestMapping("/author/{id}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findAuthorById(@PathVariable("id") Long id, Model model) {
        final Author author = authorService.findAuthorById(id);

        model.addAttribute("author", author);
        return "list-author";
    }

    @GetMapping("/addAuthor")
    @PreAuthorize("hasAuthority('library:write')")
    public String showCreateForm(Author author) {
        return "add-author";
    }

    @RequestMapping("/add-author")
    @PreAuthorize("hasAuthority('library:write')")
    public String createAuthor(Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-author";
        }

        authorService.createAuthor(author);
        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }

    @GetMapping("/updateAuthor/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final Author author = authorService.findAuthorById(id);

        model.addAttribute("author", author);
        return "update-author";
    }

    @RequestMapping("/update-author/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String updateAuthor(@PathVariable("id") Long id, Author author, BindingResult result, Model model) {
        if (result.hasErrors()) {
            author.setId(id);
            return "update-author";
        }

        authorService.updateAuthor(author);
        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }

    @RequestMapping("/remove-author/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String deleteAuthor(@PathVariable("id") Long id, Model model) {
        authorService.deleteAuthorById(id);

        model.addAttribute("author", authorService.findAllAuthors());
        return "redirect:/authors";
    }
}
