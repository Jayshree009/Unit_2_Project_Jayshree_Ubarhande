package com.example.academy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String parentName;
    @Column(nullable = false) private Integer rating; // 1..5
    @Column(nullable = false, columnDefinition = "TEXT") private String comment;

    private Boolean approved = Boolean.FALSE;
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
}
