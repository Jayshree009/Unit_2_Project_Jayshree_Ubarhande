package com.example.academy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Announcement {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    private LocalDateTime publishedAt = LocalDateTime.now();

    // Simple MVP: author is just a name string
    private String authorName;

    // getters/setters
}
