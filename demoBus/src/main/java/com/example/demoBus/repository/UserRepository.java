package com.example.demoBus.repository;

import com.example.demoBus.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Used to check if an email is already registered
    User findByEmail(String email);
}