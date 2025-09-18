// src/main/java/com/codewithmusashi/elogbook/repository/NotificationRepository.java
package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Notification;
import com.codewithmusashi.elogbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserOrderByCreatedAtDesc(User user);
}
