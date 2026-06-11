package com.openwebinars.todo.rest.dto;

import com.openwebinars.todo.rest.model.category.Category;

public record CategoryDto(
        Long id,
        String name
) {

    public static CategoryDto of(Category category) {
        return new CategoryDto(
                category.getId(),
                category.getName()
        );
    }
}