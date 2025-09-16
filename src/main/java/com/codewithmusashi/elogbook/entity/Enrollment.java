package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;

@Data
@Entity
@Table(name = "enrollments")
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "requested_by_user_id")
    private User requestedByUser;

    @Enumerated(EnumType.STRING)
    private UserRoleType userRoleType;

    @Enumerated(EnumType.STRING)
    private ApprovalStatus approvalStatus;

    @Column
    private Date enrollmentDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    public enum UserRoleType {
        STUDENT, TEACHER
    }

    public enum ApprovalStatus {
        PENDING, APPROVED, REJECTED
    }

    public enum Status {
        PENDING, ACTIVE, COMPLETED, INCOMPLETE
    }
}