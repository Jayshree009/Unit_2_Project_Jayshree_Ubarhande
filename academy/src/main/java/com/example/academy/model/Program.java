package com.example.academy.model;

import jakarta.persistence.*;

@Entity
public class Program {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true) private String name;
    private String ageGroup;        // e.g. "2-3 years"
    @Column(columnDefinition = "TEXT") private String description;

    // getters/setters
}
