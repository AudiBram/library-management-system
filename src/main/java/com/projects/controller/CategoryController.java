package com.projects.controller;

import com.projects.models.Category;
import com.projects.service.CategoryService;
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
public class CategoryController {

    private final CategoryService categoryService;

    @RequestMapping("/categories")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findAllCategories(Model model) {
        final List<Category> categories = categoryService.findAllCategories();

        model.addAttribute("categories", categories);
        return "list-categories";
    }

    @RequestMapping("/category/{id}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_STUDENT')")
    public String findCategoryById(@PathVariable("id") Long id, Model model) {
        final Category category = categoryService.findCategoryById(id);

        model.addAttribute("category", category);
        return "list-category";
    }

    @GetMapping("/addCategory")
    @PreAuthorize("hasAuthority('library:write')")
    public String showCreateForm(Category category) {
        return "add-category";
    }

    @RequestMapping("/add-category")
    @PreAuthorize("hasAuthority('library:write')")
    public String createCategory(Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-category";
        }

        categoryService.createCategory(category);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }

    @GetMapping("/updateCategory/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        final Category category = categoryService.findCategoryById(id);

        model.addAttribute("category", category);
        return "update-category";
    }

    @RequestMapping("/update-category/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String updateCategory(@PathVariable("id") Long id, Category category, BindingResult result, Model model) {
        if (result.hasErrors()) {
            category.setId(id);
            return "update-category";
        }

        categoryService.updateCategory(category);
        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }

    @RequestMapping("/remove-category/{id}")
    @PreAuthorize("hasAuthority('library:write')")
    public String deleteCategory(@PathVariable("id") Long id, Model model) {
        categoryService.deleteCategoryById(id);

        model.addAttribute("category", categoryService.findAllCategories());
        return "redirect:/categories";
    }

}
