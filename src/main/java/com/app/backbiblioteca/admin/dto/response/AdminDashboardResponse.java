package com.app.backbiblioteca.admin.dto.response;

import java.util.Map;
import com.app.backbiblioteca.loan.dto.response.MostBorrowedBookResponse;
import java.util.List;

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
        Map<String, Long> booksByOrigin,
        long totalLoans,
        long activeLoans,
        long returnedLoans,
        long overdueLoans,
        List<MostBorrowedBookResponse> mostBorrowedBooks
) {
}
