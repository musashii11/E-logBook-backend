// src/main/java/com/codewithmusashi/elogbook/service/NotificationService.java
package com.codewithmusashi.elogbook.service;

import com.codewithmusashi.elogbook.entity.Notification;
import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    @Autowired private NotificationRepository repo;

    public void send(User target, String title, String message, Notification.Type type) {
        Notification n = new Notification();
        n.setUser(target);
        n.setTitle(title);
        n.setMessage(message);
        n.setType(type);
        repo.save(n);
    }
}
