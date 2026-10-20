package com.app.backbiblioteca.admin.controller;

import com.app.backbiblioteca.admin.dto.response.AdminDashboardResponse;
import com.app.backbiblioteca.admin.service.AdminDashboardService;
import com.app.backbiblioteca.loan.dto.response.MostBorrowedBookResponse;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AdminDashboardControllerTest {

    private final AdminDashboardService adminDashboardService =
            org.mockito.Mockito.mock(AdminDashboardService.class);
    private final MockMvc mockMvc = MockMvcBuilders
            .standaloneSetup(new AdminDashboardController(adminDashboardService))
            .build();

    @Test
    void shouldReturnDashboardForRequest() throws Exception {
        when(adminDashboardService.getDashboard()).thenReturn(dashboard());

        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBooks").value(8))
                .andExpect(jsonPath("$.activeBooks").value(4))
                .andExpect(jsonPath("$.totalStock").value(9))
                .andExpect(jsonPath("$.availableStock").value(7))
                .andExpect(jsonPath("$.totalUsers").value(8))
                .andExpect(jsonPath("$.activeLoans").value(3))
                .andExpect(jsonPath("$.booksByStatus.AVAILABLE").value(1))
                .andExpect(jsonPath("$.booksByOrigin.PURCHASED").value(2))
                .andExpect(jsonPath("$.mostBorrowedBooks[0].bookId").value(2))
                .andExpect(jsonPath("$.mostBorrowedBooks[0].borrowCount").value(7));

        verify(adminDashboardService).getDashboard();
    }

    private AdminDashboardResponse dashboard() {
        return new AdminDashboardResponse(
                8, 4, 4, 9, 7, 8, 6, 2,
                Map.of("AVAILABLE", 1L),
                Map.of("PURCHASED", 2L),
                10, 3, 5, 2,
                List.of(new MostBorrowedBookResponse(2L, "The Pragmatic Programmer", 7))
        );
    }
}
