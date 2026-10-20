package com.app.backbiblioteca.user.service;

import com.app.backbiblioteca.user.domain.Roles;
import com.app.backbiblioteca.user.domain.User;
import com.app.backbiblioteca.user.dto.request.UserCreateRequest;
import com.app.backbiblioteca.user.dto.request.UserUpdateRequest;
import com.app.backbiblioteca.user.dto.response.UserResponse;
import com.app.backbiblioteca.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserResponse getUser(Long id) {
        return userRepository.findById(id)
                .filter(User::isActive)
                .map(UserResponse::new)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }

    public List<UserResponse> getAllUsers() {
        return userRepository.findAll().stream()
                .filter(User::isActive)
                .map(UserResponse::new)
                .toList();
    }

    public UserResponse registerUser(UserCreateRequest userRequest) {
        if (userRepository.existsByEmail(userRequest.email())) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = userRequest.toEntity();
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if (user.getRole() == null) {
            user.setRole(Roles.CUSTOMER);
        }
        user.setActive(true);

        return new UserResponse(userRepository.save(user));
    }

    public UserResponse updateUser(Long id, UserUpdateRequest userRequest) {
        User user = userRepository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setFirstName(userRequest.firstName());
        user.setLastName(userRequest.lastName());
        user.setEmail(userRequest.email());

        return new UserResponse(userRepository.save(user));
    }

    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .filter(User::isActive)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setActive(false);

        userRepository.save(user);
    }
}
