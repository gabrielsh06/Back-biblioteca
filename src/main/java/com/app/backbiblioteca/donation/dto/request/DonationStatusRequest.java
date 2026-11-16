package com.app.backbiblioteca.donation.dto.request;

import com.app.backbiblioteca.donation.domain.DonationStatus;
import jakarta.validation.constraints.NotNull;

public record DonationStatusRequest(
        @NotNull(message = "El estado es obligatorio") DonationStatus status,
        String rejectionReason
) {
}
