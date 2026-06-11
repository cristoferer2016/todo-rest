package com.openwebinars.todo.rest.repos;

import com.openwebinars.todo.rest.model.Priority;
import com.openwebinars.todo.rest.model.Task;
import com.openwebinars.todo.rest.model.TaskStatus;
import com.openwebinars.todo.rest.model.Tag.Tag;
import com.openwebinars.todo.rest.model.category.Category;
import com.openwebinars.todo.rest.users.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByAuthor(User author);

    List<Task> findByAuthorAndTitleContainingIgnoreCase(User author, String title);

    List<Task> findByAuthorAndDescriptionContainingIgnoreCase(User author, String description);

    List<Task> findByAuthorAndCompleted(User author, boolean completed);

    List<Task> findByAuthorAndDeadlineBefore(User author, LocalDateTime deadline);

    List<Task> findByAuthorAndPriority(User author, Priority priority);

    List<Task> findByAuthorAndStatus(User author, TaskStatus status);

    List<Task> findByAuthorAndTagsContaining(User author, Tag tag);

    List<Task> findByAuthorAndCategory(User author, Category category);
}