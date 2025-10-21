package com.example.academy.model;

import jakarta.persistence.*;

@Entity
public class Teacher {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) private String fullName;
    @Column(columnDefinition = "TEXT") private String bio;
    private String photoUrl;
    private String email;
    private String phone;

    // getters/setters
}
