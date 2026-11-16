package com.app.backbiblioteca.donation.controller;

import com.app.backbiblioteca.donation.dto.request.DonationCreateRequest;
import com.app.backbiblioteca.donation.dto.response.DonationResponse;
import com.app.backbiblioteca.donation.service.DonationService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/donations")
public class DonationController {
    private final DonationService donationService;

    public DonationController(DonationService donationService) {
        this.donationService = donationService;
    }

    @PostMapping
    public ResponseEntity<DonationResponse> create(@Valid @RequestBody DonationCreateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(donationService.create(request));
    }
}
