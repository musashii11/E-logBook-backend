package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@Entity
@Table(name = "approval_actions")
public class ApprovalAction {
    public enum Action{
        APPROVE,
        REJECT
    }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "request_id", nullable = false)
    private ApprovalRequest request;

    @ManyToOne @JoinColumn(name = "approver_id", nullable = false)
    private User approver;

    @Column(nullable = false)
    private Integer stepNo = 1;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private Action action;

    @Column(columnDefinition = "TEXT")
    private String remarks;

    @Column(nullable = false)
    private LocalDateTime createdAt = java.time.LocalDateTime.now();
}
