package com.openwebinars.todo.rest.service;

import com.openwebinars.todo.rest.model.category.Category;
import com.openwebinars.todo.rest.repos.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    public Category findById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category edit(Long id, Category category) {
        return categoryRepository.findById(id)
                .map(existingCategory -> {
                    existingCategory.setName(category.getName());
                    return categoryRepository.save(existingCategory);
                })
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}