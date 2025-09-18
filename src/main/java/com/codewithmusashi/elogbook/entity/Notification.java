// src/main/java/com/codewithmusashi/elogbook/entity/Notification.java
package com.codewithmusashi.elogbook.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {
    public enum Type { INFO, ACTION_REQUIRED }

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING) @Column(nullable = false)
    private Type type = Type.INFO;

    @Column(nullable = false) private String title;
    @Column(columnDefinition = "TEXT") private String message;

    private LocalDateTime readAt;
    @Column(nullable = false) private LocalDateTime createdAt = LocalDateTime.now();
}
