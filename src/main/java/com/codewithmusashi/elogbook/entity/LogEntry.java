package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "log_entries")
public class LogEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @ManyToOne
    @JoinColumn(name = "competency_id")
    private Competency competency; // Optional for daily logs

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill; // Optional

    @ManyToOne
    @JoinColumn(name = "involvement_level_id", nullable = false)
    private InvolvementLevel involvementLevel;

    @Column(columnDefinition = "TEXT")
    private String activityTopic;

    private LocalDate startDate;

    private LocalDate endDate;

    private Integer procedureCount;

    @Column(columnDefinition = "TEXT")
    private String reflections;

    private Boolean feedbackReceived;

    @Column(columnDefinition = "TEXT")
    private String learnings;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    public enum Status {
        PENDING, VERIFIED, REJECTED
    }
}