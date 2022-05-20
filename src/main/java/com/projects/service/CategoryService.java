package com.projects.service;

import com.projects.exception.NotFoundException;
import com.projects.models.Category;
import com.projects.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.transaction.annotation.Propagation.SUPPORTS;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional(readOnly = true, propagation = SUPPORTS)
    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Category with id %d not found", id)));
    }

    public void createCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategoryById(Long id) {
        boolean exists = categoryRepository.existsById(id);
        if(!exists) {
            throw new NotFoundException(String.format("Category with id %d not found", id));
        }
        categoryRepository.deleteById(id);
    }

}
