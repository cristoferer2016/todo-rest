package com.openwebinars.todo.rest.users;

public record EditProfileDto(
        String username,
        String email,
        String fullname,
        String password
) {
}