package com.app.backbiblioteca.admin.dto.response;

import java.util.Map;

public record AdminDashboardResponse(
        long totalBooks,
        long activeBooks,
        long inactiveBooks,
        long totalStock,
        long availableStock,
        long totalUsers,
        long activeUsers,
        long inactiveUsers,
        Map<String, Long> booksByStatus,
        Map<String, Long> booksByOrigin
) {
}
