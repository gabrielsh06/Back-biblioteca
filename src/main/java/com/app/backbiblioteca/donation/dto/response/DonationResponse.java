package com.app.backbiblioteca.donation.dto.response;

import com.app.backbiblioteca.donation.domain.Donation;
import com.app.backbiblioteca.donation.domain.BookCondition;
import com.app.backbiblioteca.donation.domain.DonationStatus;

import java.time.LocalDateTime;

public record DonationResponse(
        Long id,
        String title,
        String author,
        String ISBN,
        String category,
        BookCondition condition,
        String donorName,
        String donorEmail,
        String notes,
        DonationStatus status,
        LocalDateTime createdAt,
        LocalDateTime reviewedAt,
        String reviewedBy,
        String rejectionReason
) {
    public DonationResponse(Donation donation) {
        this(donation.getId(), donation.getTitle(), donation.getAuthor(), donation.getISBN(),
                donation.getCategory().name(), donation.getCondition(), donation.getDonorName(),
                donation.getDonorEmail(), donation.getNotes(), donation.getStatus(),
                donation.getCreatedAt(), donation.getReviewedAt(), donation.getReviewedBy(),
                donation.getRejectionReason());
    }
}
