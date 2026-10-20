package com.app.backbiblioteca.loan.controller;

import com.app.backbiblioteca.loan.dto.response.LoanResponse;
import com.app.backbiblioteca.loan.service.LoanService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin/loans")
public class AdminLoanController {

    private final LoanService loanService;

    public AdminLoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping
    public ResponseEntity<List<LoanResponse>> allLoans() {
        return ResponseEntity.ok(loanService.findAll());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<LoanResponse>> overdueLoans() {
        loanService.markOverdue();
        return ResponseEntity.ok(loanService.findOverdue());
    }
}
