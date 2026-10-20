package com.app.backbiblioteca.loan.controller;

import com.app.backbiblioteca.loan.dto.request.LoanCreateRequest;
import com.app.backbiblioteca.loan.dto.response.LoanResponse;
import com.app.backbiblioteca.loan.service.LoanService;
import com.app.backbiblioteca.user.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final LoanService loanService;

    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @PostMapping
    public ResponseEntity<LoanResponse> create(
            @Valid @RequestBody LoanCreateRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(loanService.create(request, user.getId()));
    }

    @GetMapping("/my-loans")
    public ResponseEntity<List<LoanResponse>> myLoans(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(loanService.findMine(user.getId()));
    }

    @PutMapping("/{id}/return")
    public ResponseEntity<LoanResponse> returnLoan(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(loanService.returnLoan(id, user.getId(), user.getRole().name().equals("ADMIN")));
    }
}
