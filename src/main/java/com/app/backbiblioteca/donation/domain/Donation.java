package com.app.backbiblioteca.donation.domain;

import com.app.backbiblioteca.book.domain.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "donations")
public class Donation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String author;
    private String ISBN;
    @Enumerated(EnumType.STRING)
    private Category category;
    @Enumerated(EnumType.STRING)
    private BookCondition condition;
    private String donorName;
    private String donorEmail;
    private String notes;
    @Enumerated(EnumType.STRING)
    private DonationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime reviewedAt;
    private String reviewedBy;
    private String rejectionReason;
}
