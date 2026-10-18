package com.app.backbiblioteca.user.repository;

import com.app.backbiblioteca.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
