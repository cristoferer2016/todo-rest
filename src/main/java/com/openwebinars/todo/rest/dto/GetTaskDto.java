package com.openwebinars.todo.rest.dto;

import com.openwebinars.todo.rest.model.Priority;
import com.openwebinars.todo.rest.model.Task;
import com.openwebinars.todo.rest.model.TaskStatus;
import com.openwebinars.todo.rest.users.NewUserResponse;

import java.time.LocalDateTime;
import java.util.List;

public record GetTaskDto(
        Long id,
        String title,
        String description,
        boolean completed,
        LocalDateTime createdAt,
        LocalDateTime deadline,
        Priority priority,
        TaskStatus status,
        CategoryDto category,
        List<TagDto> tags,
        NewUserResponse author
) {

    public static GetTaskDto of(Task t) {
        return new GetTaskDto(
                t.getId(),
                t.getTitle(),
                t.getDescription(),
                t.isCompleted(),
                t.getCreatedAt(),
                t.getDeadline(),
                t.getPriority(),
                t.getStatus(),
                t.getCategory() != null ? CategoryDto.of(t.getCategory()) : null,
                t.getTags()
                        .stream()
                        .map(TagDto::of)
                        .toList(),
                NewUserResponse.of(t.getAuthor())
        );
    }
}