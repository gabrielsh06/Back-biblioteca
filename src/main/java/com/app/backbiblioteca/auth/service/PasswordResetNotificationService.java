package com.app.backbiblioteca.auth.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class PasswordResetNotificationService {
    private static final Logger log = LoggerFactory.getLogger(PasswordResetNotificationService.class);
    private final JavaMailSender mailSender;
    private final String frontendUrl;
    private final String from;

    public PasswordResetNotificationService(
            JavaMailSender mailSender,
            @Value("${frontend.url:http://localhost:4200}") String frontendUrl,
            @Value("${spring.mail.from:no-reply@biblioteca.local}") String from) {
        this.mailSender = mailSender;
        this.frontendUrl = frontendUrl;
        this.from = from;
    }

    public void send(String email, String rawToken) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(email);
        message.setSubject("Restablecer contraseña");
        message.setText("Restablece tu contraseña en: " + frontendUrl
                + "/restablecer-password?token=" + rawToken);
        try {
            mailSender.send(message);
        } catch (RuntimeException ex) {
            log.error("No se pudo enviar el correo de recuperación a {}", email, ex);
        }
    }
}
