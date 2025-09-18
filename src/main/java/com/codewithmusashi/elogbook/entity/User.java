// src/main/java/com/codewithmusashi/elogbook/entity/User.java
package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "users")
public class User {

    public enum Role { HOD, TEACHER, STUDENT }
    public enum ApprovalStatus { PENDING, APPROVED, REJECTED }
    public enum ActiveStatus { ACTIVE, INACTIVE }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false) private String username;
    @Column(nullable = false) private String password; // hashed
    @Column(unique = true, nullable = false) private String email;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Role role;

    @Column(nullable = false) private String fullName;
    @Column(nullable = false) private String collegeName;

    private String rollNumber;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private ApprovalStatus approvalStatus = ApprovalStatus.PENDING;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private ActiveStatus activeStatus = ActiveStatus.INACTIVE;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private java.util.Map<String, Object> metadata;   // or com.fasterxml.jackson.databind.JsonNode

    @ManyToOne @JoinColumn(name = "department_id")
    private Department department;

    @CreationTimestamp private LocalDateTime createdAt;
    @UpdateTimestamp  private LocalDateTime updatedAt;
}
