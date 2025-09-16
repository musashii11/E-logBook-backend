package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
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
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "competency_id")
    private Competency competency;

    @ManyToOne
    @JoinColumn(name = "skill_id")
    private Skill skill;

    @ManyToOne
    @JoinColumn(name = "involvement_level_id", nullable = false)
    private InvolvementLevel involvementLevel;

    @Column(nullable = false)
    private String activityTopic;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column
    private Date dueDate;

    @Column(nullable = false)
    private Integer procedureCount;

    @Column(columnDefinition = "TEXT")
    private String reflections;

    @Column(nullable = false)
    private Boolean feedbackReceived;

    @Column(columnDefinition = "TEXT")
    private String learnings;

    @Column
    private String customFields;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    public enum Status {
        PENDING, VERIFIED, REJECTED
    }
}