package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "portfolios")
public class Portfolio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "log_entry_id")
    private LogEntry logEntry;

    @ManyToOne
    @JoinColumn(name = "verification_request_id")
    private VerificationRequest verificationRequest;

    @Column(nullable = false)
    private String filePath;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    @Column(columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "uploaded_by", nullable = false)
    private User uploadedBy;

    @Column(nullable = false)
    private LocalDateTime uploadedAt;

    public enum FileType {
        PDF, IMAGE
    }
}