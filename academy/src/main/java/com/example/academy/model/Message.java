package com.example.academy.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Message {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String fromName;
    @Column(nullable = false) private String fromEmail;
    private String subject;

    @Column(nullable = false, columnDefinition = "TEXT") private String body;

    private Boolean readFlag = Boolean.FALSE;
    private LocalDateTime createdAt = LocalDateTime.now();

    // getters/setters
}
