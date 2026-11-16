package com.app.backbiblioteca.donation.service;

import com.app.backbiblioteca.donation.domain.Donation;
import com.app.backbiblioteca.donation.domain.DonationStatus;
import com.app.backbiblioteca.donation.dto.request.DonationCreateRequest;
import com.app.backbiblioteca.donation.dto.request.DonationStatusRequest;
import com.app.backbiblioteca.donation.dto.response.DonationResponse;
import com.app.backbiblioteca.donation.repository.DonationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DonationService {
    private final DonationRepository donationRepository;

    public DonationService(DonationRepository donationRepository) {
        this.donationRepository = donationRepository;
    }

    @Transactional
    public DonationResponse create(DonationCreateRequest request) {
        Donation donation = new Donation();
        donation.setTitle(request.title());
        donation.setAuthor(request.author());
        donation.setISBN(request.ISBN());
        donation.setCategory(request.category());
        donation.setCondition(request.condition());
        donation.setDonorName(request.donorName());
        donation.setDonorEmail(request.donorEmail());
        donation.setNotes(request.notes());
        donation.setStatus(DonationStatus.PENDING);
        donation.setCreatedAt(LocalDateTime.now());
        return new DonationResponse(donationRepository.save(donation));
    }

    @Transactional(readOnly = true)
    public List<DonationResponse> findAll(DonationStatus status) {
        List<Donation> donations = status == null
                ? donationRepository.findAllByOrderByCreatedAtDesc()
                : donationRepository.findByStatusOrderByCreatedAtDesc(status);
        return donations.stream().map(DonationResponse::new).toList();
    }

    @Transactional(readOnly = true)
    public DonationResponse findById(Long id) {
        return donationRepository.findById(id).map(DonationResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("Donación no encontrada"));
    }

    @Transactional
    public DonationResponse updateStatus(Long id, DonationStatusRequest request, String reviewer) {
        if (request.status() == DonationStatus.PENDING) {
            throw new IllegalArgumentException("Solo se puede cambiar una donación a APPROVED o REJECTED");
        }
        if (request.status() == DonationStatus.REJECTED
                && (request.rejectionReason() == null || request.rejectionReason().isBlank())) {
            throw new IllegalArgumentException("El motivo de rechazo es obligatorio");
        }
        Donation donation = donationRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Donación no encontrada"));
        donation.setStatus(request.status());
        donation.setRejectionReason(request.status() == DonationStatus.REJECTED
                ? request.rejectionReason().trim() : null);
        donation.setReviewedAt(LocalDateTime.now());
        donation.setReviewedBy(reviewer);
        return new DonationResponse(donationRepository.save(donation));
    }

    @Transactional
    public void delete(Long id) {
        if (!donationRepository.existsById(id)) {
            throw new EntityNotFoundException("Donación no encontrada");
        }
        donationRepository.deleteById(id);
    }
}
