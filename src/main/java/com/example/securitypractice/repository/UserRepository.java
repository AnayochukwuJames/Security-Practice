package com.example.securitypractice.repository;

import com.example.securitypractice.enums.Role;
import com.example.securitypractice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String username);

    User findByRole(Role role);

    boolean existsByEmail(String email);
}
