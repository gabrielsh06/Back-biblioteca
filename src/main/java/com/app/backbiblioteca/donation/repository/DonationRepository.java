package com.app.backbiblioteca.donation.repository;

import com.app.backbiblioteca.donation.domain.Donation;
import com.app.backbiblioteca.donation.domain.DonationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DonationRepository extends JpaRepository<Donation, Long> {
    List<Donation> findAllByOrderByCreatedAtDesc();
    List<Donation> findByStatusOrderByCreatedAtDesc(DonationStatus status);
}
