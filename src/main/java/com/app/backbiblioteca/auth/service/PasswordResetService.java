package com.app.backbiblioteca.auth.service;

import com.app.backbiblioteca.auth.domain.PasswordResetToken;
import com.app.backbiblioteca.auth.dto.request.ResetPasswordRequest;
import com.app.backbiblioteca.auth.repository.PasswordResetTokenRepository;
import com.app.backbiblioteca.user.domain.User;
import com.app.backbiblioteca.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.HexFormat;

@Service
public class PasswordResetService {
    private static final int TOKEN_MINUTES = 30;
    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetNotificationService notificationService;
    private final SecureRandom secureRandom = new SecureRandom();

    public PasswordResetService(UserRepository userRepository, PasswordResetTokenRepository tokenRepository,
                                PasswordEncoder passwordEncoder, PasswordResetNotificationService notificationService) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.notificationService = notificationService;
    }

    @Transactional
    public void request(String email) {
        userRepository.findByEmail(email).filter(User::isActive).ifPresent(user -> {
            String rawToken = generateToken();
            PasswordResetToken token = new PasswordResetToken();
            token.setUser(user);
            token.setTokenHash(hash(rawToken));
            token.setExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_MINUTES));
            tokenRepository.save(token);
            notificationService.send(user.getEmail(), rawToken);
        });
    }

    @Transactional
    public void reset(ResetPasswordRequest request) {
        PasswordResetToken token = tokenRepository.findByTokenHash(hash(request.token()))
                .filter(value -> value.getUsedAt() == null)
                .filter(value -> value.getExpiresAt().isAfter(LocalDateTime.now()))
                .orElseThrow(() -> new IllegalArgumentException("El token es inválido o ha vencido"));
        token.getUser().setPassword(passwordEncoder.encode(request.newPassword()));
        token.getUser().setActive(true);
        token.setUsedAt(LocalDateTime.now());
        userRepository.save(token.getUser());
        tokenRepository.save(token);
    }

    private String generateToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hash(String token) {
        try {
            return HexFormat.of().formatHex(MessageDigest.getInstance("SHA-256")
                    .digest(token.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("No se pudo preparar el token de recuperación", ex);
        }
    }
}
