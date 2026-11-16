package com.app.backbiblioteca.donation.controller;

import com.app.backbiblioteca.donation.domain.DonationStatus;
import com.app.backbiblioteca.donation.dto.request.DonationStatusRequest;
import com.app.backbiblioteca.donation.dto.response.DonationResponse;
import com.app.backbiblioteca.donation.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.app.backbiblioteca.user.domain.User;

import java.util.List;

@RestController
@RequestMapping("/api/admin/donations")
public class AdminDonationController {
    private final DonationService donationService;

    public AdminDonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @GetMapping
    public ResponseEntity<List<DonationResponse>> findAll(@RequestParam(required = false) DonationStatus status) {
        return ResponseEntity.ok(donationService.findAll(status));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DonationResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(donationService.findById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<DonationResponse> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody DonationStatusRequest request,
            @AuthenticationPrincipal User admin) {
        return ResponseEntity.ok(donationService.updateStatus(id, request, admin.getEmail()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        donationService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
