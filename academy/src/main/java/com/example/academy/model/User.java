package com.example.academy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String fullName;
    @Column(nullable = false, unique = true) private String email;
    @Column(nullable = false) private String role; // ADMIN / PARENT

    @Column(nullable = false) private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
}
