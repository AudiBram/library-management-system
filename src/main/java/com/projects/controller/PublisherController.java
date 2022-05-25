package com.projects.controller;

import com.projects.models.Publisher;
import com.projects.service.PublisherService;
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
public class PublisherController {

    private final PublisherService publisherService;

    @RequestMapping("/publishers")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findAllPublishers(Model model) {
        final List<Publisher> publishers = publisherService.findAllPublishers();

        model.addAttribute("publishers", publishers);
        return "list-publishers";
    }

    @RequestMapping("/publisher/{id}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findPublisherById(@PathVariable("id") Long id, Model model) {
        final Publisher publisher = publisherService.findPublisherById(id);

        model.addAttribute("publisher", publisher);
        return "list-publisher";
    }

    @GetMapping("/addPublisher")
    @PreAuthorize("hasAuthority('library:write')")
    public String showCreateForm(Publisher publisher) {
        return "add-publisher";
    }

    @RequestMapping("/add-publisher")
    @PreAuthorize("hasAuthority('library:write')")
    public String createPublisher(Publisher publisher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-publisher";
        }

        publisherService.createPublisher(publisher);
        model.addAttribute("publisher", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }

    @GetMapping("/updatePublisher/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final Publisher publisher = publisherService.findPublisherById(id);

        model.addAttribute("publisher", publisher);
        return "update-publisher";
    }

    @RequestMapping("/update-publisher/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String updatePublisher(@PathVariable("id") Long id, Publisher publisher, BindingResult result, Model model) {
        if (result.hasErrors()) {
            publisher.setId(id);
            return "update-publishers";
        }

        publisherService.updatePublisher(publisher);
        model.addAttribute("publisher", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }

    @RequestMapping("/remove-publisher/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String deletePublisher(@PathVariable("id") Long id, Model model) {
        publisherService.deletePublisherById(id);

        model.addAttribute("publisher", publisherService.findAllPublishers());
        return "redirect:/publishers";
    }


}
