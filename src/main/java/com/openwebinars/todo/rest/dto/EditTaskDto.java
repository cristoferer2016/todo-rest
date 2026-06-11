package com.openwebinars.todo.rest.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.openwebinars.todo.rest.model.Priority;
import com.openwebinars.todo.rest.model.TaskStatus;

import java.time.LocalDateTime;
import java.util.Set;

public record EditTaskDto(
        String title,
        String description,
        boolean completed,

        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime deadline,

        Priority priority,
        TaskStatus status,
        Set<Long> tagIds,
        Long categoryId

) {
}