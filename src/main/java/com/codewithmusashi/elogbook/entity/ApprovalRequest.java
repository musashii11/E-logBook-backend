package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "approval_requests")
public class ApprovalRequest {

    public enum Module {
        CASE,
        THESIS
    }

    public enum Status {
        PENDING,
        APPROVED,
        REJECTED
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 32)
    private Module module;

    @Column(nullable = false)
    private long   moduleId;

    @Column
    private Integer currentStep;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Status status = Status.PENDING;

    @Column(nullable = false)
    private LocalDateTime createdAt = java.time.LocalDateTime.now();

    @Column
    private LocalDateTime decidedAt;
}
