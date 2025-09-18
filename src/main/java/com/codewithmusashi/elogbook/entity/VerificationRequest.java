package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity @Table(name = "verification_requests")
@Data
public class VerificationRequest {

    public enum Type { STUDENT_REGISTRATION }
    public enum Status { PENDING, APPROVED, REJECTED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Type type = Type.STUDENT_REGISTRATION;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Status status = Status.PENDING;

    @ManyToOne @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime decidedAt;

    private String remark; // optional reason on reject
}
