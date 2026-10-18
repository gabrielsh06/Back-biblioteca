package com.app.backbiblioteca.user.repository;

import com.app.backbiblioteca.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
