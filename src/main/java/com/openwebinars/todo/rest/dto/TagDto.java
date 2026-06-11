package com.openwebinars.todo.rest.dto;

import com.openwebinars.todo.rest.model.Tag.Tag;

public record TagDto(
        Long id,
        String name
) {

    public static TagDto of(Tag tag) {
        return new TagDto(
                tag.getId(),
                tag.getName()
        );
    }
}