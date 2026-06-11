package com.openwebinars.todo.rest.service;

import com.openwebinars.todo.rest.dto.EditTaskDto;
import com.openwebinars.todo.rest.error.TaskNotFoundException;
import com.openwebinars.todo.rest.model.Priority;
import com.openwebinars.todo.rest.model.Task;
import com.openwebinars.todo.rest.model.TaskStatus;
import com.openwebinars.todo.rest.model.Tag.Tag;
import com.openwebinars.todo.rest.model.category.Category;
import com.openwebinars.todo.rest.repos.CategoryRepository;
import com.openwebinars.todo.rest.repos.TagRepository;
import com.openwebinars.todo.rest.repos.TaskRepository;
import com.openwebinars.todo.rest.users.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import com.openwebinars.todo.rest.dto.DashboardDto;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;

    public List<Task> findAll() {
        List<Task> result = taskRepository.findAll();

        if (result.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return result;
    }

    public List<Task> findByAuthor(User author) {
        List<Task> result = taskRepository.findByAuthor(author);

        if (result.isEmpty()) {
            throw new TaskNotFoundException();
        }

        return result;
    }

    public Task findById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public Task save(EditTaskDto cmd, User author) {
        Category category = findCategory(cmd.categoryId());
        Set<Tag> tags = findTags(cmd.tagIds());

        Task task = Task.builder()
                .title(cmd.title())
                .description(cmd.description())
                .completed(cmd.completed())
                .deadline(cmd.deadline())
                .priority(cmd.priority() != null ? cmd.priority() : Priority.MEDIUM)
                .status(cmd.status() != null ? cmd.status() : TaskStatus.PENDING)
                .category(category)
                .tags(tags)
                .author(author)
                .build();

        return taskRepository.save(task);
    }

    public Task edit(EditTaskDto cmd, Long id) {
        return taskRepository.findById(id)
                .map(task -> {
                    Category category = findCategory(cmd.categoryId());
                    Set<Tag> tags = findTags(cmd.tagIds());

                    task.setTitle(cmd.title());
                    task.setDescription(cmd.description());
                    task.setCompleted(cmd.completed());
                    task.setDeadline(cmd.deadline());
                    task.setPriority(cmd.priority() != null ? cmd.priority() : Priority.MEDIUM);
                    task.setStatus(cmd.status() != null ? cmd.status() : TaskStatus.PENDING);
                    task.setCategory(category);
                    task.setTags(tags);

                    return taskRepository.save(task);
                })
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public void delete(Long id) {
        taskRepository.deleteById(id);
    }

    private Category findCategory(Long categoryId) {
        if (categoryId == null) {
            return null;
        }

        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
    public Task addTagToTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));

        task.addTag(tag);

        return taskRepository.save(task);
    }

    public Task removeTagFromTask(Long taskId, Long tagId) {
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException(taskId));

        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));

        task.removeTag(tag);

        return taskRepository.save(task);
    }
    public List<Task> findByTitle(User author, String title) {
        return taskRepository.findByAuthorAndTitleContainingIgnoreCase(author, title);
    }

    public List<Task> findByDescription(User author, String description) {
        return taskRepository.findByAuthorAndDescriptionContainingIgnoreCase(author, description);
    }

    public List<Task> findByCompleted(User author, boolean completed) {
        return taskRepository.findByAuthorAndCompleted(author, completed);
    }

    public List<Task> findExpired(User author) {
        return taskRepository.findByAuthorAndDeadlineBefore(author, java.time.LocalDateTime.now());
    }

    public List<Task> findByPriority(User author, Priority priority) {
        return taskRepository.findByAuthorAndPriority(author, priority);
    }

    public List<Task> findByStatus(User author, TaskStatus status) {
        return taskRepository.findByAuthorAndStatus(author, status);
    }

    public List<Task> findByCategory(User author, Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));

        return taskRepository.findByAuthorAndCategory(author, category);
    }

    public List<Task> findByTag(User author, Long tagId) {
        Tag tag = tagRepository.findById(tagId)
                .orElseThrow(() -> new RuntimeException("Tag no encontrado"));

        return taskRepository.findByAuthorAndTagsContaining(author, tag);
    }

    private Set<Tag> findTags(Set<Long> tagIds) {
        if (tagIds == null || tagIds.isEmpty()) {
            return new HashSet<>();
        }

        return new HashSet<>(tagRepository.findAllById(tagIds));
    }
    public DashboardDto getDashboard(User author) {
        List<Task> tasks = taskRepository.findByAuthor(author);

        long totalTasks = tasks.size();

        long completedTasks = tasks.stream()
                .filter(Task::isCompleted)
                .count();

        long pendingTasks = tasks.stream()
                .filter(task -> !task.isCompleted())
                .count();

        long expiredTasks = tasks.stream()
                .filter(task -> task.getDeadline() != null)
                .filter(task -> task.getDeadline().isBefore(LocalDateTime.now()))
                .count();

        Map<String, Long> tasksByPriority = tasks.stream()
                .filter(task -> task.getPriority() != null)
                .collect(Collectors.groupingBy(
                        task -> task.getPriority().name(),
                        Collectors.counting()
                ));

        Map<String, Long> tasksByStatus = tasks.stream()
                .filter(task -> task.getStatus() != null)
                .collect(Collectors.groupingBy(
                        task -> task.getStatus().name(),
                        Collectors.counting()
                ));

        Map<String, Long> tasksByCategory = tasks.stream()
                .filter(task -> task.getCategory() != null)
                .collect(Collectors.groupingBy(
                        task -> task.getCategory().getName(),
                        Collectors.counting()
                ));

        Map<String, Long> tasksByTag = tasks.stream()
                .flatMap(task -> task.getTags().stream())
                .collect(Collectors.groupingBy(
                        tag -> tag.getName(),
                        Collectors.counting()
                ));

        return new DashboardDto(
                totalTasks,
                completedTasks,
                pendingTasks,
                expiredTasks,
                tasksByPriority,
                tasksByStatus,
                tasksByCategory,
                tasksByTag
        );
    }
}