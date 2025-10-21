package com.example.academy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Tour {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String parentName;
    @Column(nullable = false) private String email;
    private String phone;

    private String childName;
    private String preferredDate;     // keep as String for MVP (or LocalDate)
    private String preferredTime;     // keep as String for MVP

    @Column(nullable = false) private String status = "PENDING"; // PENDING/CONFIRMED/CANCELLED

    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
}
