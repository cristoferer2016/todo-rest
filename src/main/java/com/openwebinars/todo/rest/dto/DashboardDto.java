package com.openwebinars.todo.rest.dto;

import java.util.Map;

public record DashboardDto(
        long totalTasks,
        long completedTasks,
        long pendingTasks,
        long expiredTasks,
        Map<String, Long> tasksByPriority,
        Map<String, Long> tasksByStatus,
        Map<String, Long> tasksByCategory,
        Map<String, Long> tasksByTag
) {
}