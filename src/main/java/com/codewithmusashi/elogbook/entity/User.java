package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false)
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "college_id", nullable = false)
    private College college;

    private String rollNumber;

    private String joiningLetterPath;

    @Enumerated(EnumType.STRING)
    private Status status;

    private String metadata;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum Role {
        ADMIN, TEACHER, STUDENT
    }

    public enum Status {
        PENDING, PENDING_VERIFICATION, ACTIVE, INACTIVE, REJECTED
    }
}