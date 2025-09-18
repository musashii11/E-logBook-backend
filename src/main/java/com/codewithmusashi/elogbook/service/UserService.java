// src/main/java/com/codewithmusashi/elogbook/service/UserService.java
package com.codewithmusashi.elogbook.service;

import com.codewithmusashi.elogbook.entity.Notification;
import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.repository.UserRepository;
import com.codewithmusashi.elogbook.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired private UserRepository userRepository;
    @Autowired private BCryptPasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private NotificationService notificationService;

    public User createUser(User creator, User toCreate) {
        // Required fields
        if (toCreate.getCollegeName() == null || toCreate.getCollegeName().isBlank())
            throw new IllegalArgumentException("College name is required");
        if (toCreate.getUsername() == null || toCreate.getUsername().isBlank())
            throw new IllegalArgumentException("Username is required");
        if (toCreate.getPassword() == null || toCreate.getPassword().isBlank())
            throw new IllegalArgumentException("Password is required");
        if (toCreate.getFullName() == null || toCreate.getFullName().isBlank())
            throw new IllegalArgumentException("Full name is required");
        if (toCreate.getRole() == null)
            throw new IllegalArgumentException("Role is required");

        // Role rules:
        // HOD can create TEACHER or STUDENT in their department (or own dept if not provided)
        // TEACHER can create STUDENT in their department
        // If creator is null (first HOD inserted manually) you can still use this to seed others
        if (creator != null) {
            switch (creator.getRole()) {
                case HOD -> {
                    if (toCreate.getDepartment() == null) toCreate.setDepartment(creator.getDepartment());
                }
                case TEACHER -> {
                    if (toCreate.getRole() != User.Role.STUDENT)
                        throw new IllegalArgumentException("Teacher can only create STUDENT");
                    toCreate.setDepartment(creator.getDepartment());
                }
                case STUDENT -> throw new IllegalArgumentException("Student cannot create users");
            }
        }
        if (toCreate.getDepartment() == null)
            throw new IllegalArgumentException("Department is required");

        // uniqueness helpers
        userRepository.findByUsername(toCreate.getUsername())
                .ifPresent(u -> { throw new IllegalArgumentException("Username already in use"); });
        if (toCreate.getEmail() != null && !toCreate.getEmail().isBlank()) {
            userRepository.findByEmail(toCreate.getEmail())
                    .ifPresent(u -> { throw new IllegalArgumentException("Email already in use"); });
        }
        if (toCreate.getRollNumber() != null && !toCreate.getRollNumber().isBlank()) {
            userRepository.findByRollNumber(toCreate.getRollNumber())
                    .ifPresent(u -> { throw new IllegalArgumentException("Roll number already in use"); });
        }

        // set onboarding defaults
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        toCreate.setApprovalStatus(User.ApprovalStatus.PENDING);
        toCreate.setActiveStatus(User.ActiveStatus.INACTIVE);
        toCreate.setCreatedAt(LocalDateTime.now());
        toCreate.setUpdatedAt(LocalDateTime.now());

        User saved = userRepository.save(toCreate);

        // notify approvers in same department
        var approvers = userRepository.findByDepartmentAndRoleIn(
                saved.getDepartment(),
                List.of(User.Role.HOD, User.Role.TEACHER)
        );
        for (User approver : approvers) {
            notificationService.send(
                    approver,
                    (saved.getRole() == User.Role.STUDENT ? "New student" : "New teacher") + " created",
                    saved.getFullName() + " (" + saved.getUsername() + ") requires approval.",
                    Notification.Type.ACTION_REQUIRED
            );
        }
        return saved;
    }

    public User approve(User approver, Long userId) {
        User target = userRepository.findById(userId).orElseThrow();
        // authorization: HOD/TEACHER in same dept; teacher cannot approve HOD
        if (approver.getDepartment() == null || target.getDepartment() == null
                || !approver.getDepartment().getId().equals(target.getDepartment().getId())) {
            throw new IllegalArgumentException("Different department");
        }
        if (approver.getRole() == User.Role.TEACHER && target.getRole() == User.Role.HOD) {
            throw new IllegalArgumentException("Teacher cannot approve HOD");
        }

        target.setApprovalStatus(User.ApprovalStatus.APPROVED);
        target.setActiveStatus(User.ActiveStatus.ACTIVE); // active on approval by default
        target.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(target);
    }

    public User reject(User approver, Long userId, String remark) {
        User target = userRepository.findById(userId).orElseThrow();
        if (approver.getDepartment() == null || target.getDepartment() == null
                || !approver.getDepartment().getId().equals(target.getDepartment().getId())) {
            throw new IllegalArgumentException("Different department");
        }
        if (approver.getRole() == User.Role.TEACHER && target.getRole() == User.Role.HOD) {
            throw new IllegalArgumentException("Teacher cannot reject HOD");
        }

        target.setApprovalStatus(User.ApprovalStatus.REJECTED);
        target.setActiveStatus(User.ActiveStatus.INACTIVE);
        target.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(target);
    }

    // HOD can toggle anyone's active; Teacher can toggle only students
    public User setActive(User actor, Long userId, boolean active) {
        User target = userRepository.findById(userId).orElseThrow();
        if (actor.getDepartment() == null || target.getDepartment() == null
                || !actor.getDepartment().getId().equals(target.getDepartment().getId())) {
            throw new IllegalArgumentException("Different department");
        }
        if (actor.getRole() == User.Role.TEACHER && target.getRole() != User.Role.STUDENT) {
            throw new IllegalArgumentException("Teacher can toggle only students");
        }
        target.setActiveStatus(active ? User.ActiveStatus.ACTIVE : User.ActiveStatus.INACTIVE);
        target.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(target);
    }

    // login rule: must be APPROVED + ACTIVE
    public Optional<User> authenticateUser(String username, String rawPassword) {
        var user = userRepository.findByUsername(username);
        if (user.isPresent()
                && passwordEncoder.matches(rawPassword, user.get().getPassword())
                && user.get().getApprovalStatus() == User.ApprovalStatus.APPROVED
                && user.get().getActiveStatus() == User.ActiveStatus.ACTIVE) {
            return user;
        }
        return Optional.empty();
    }

    public String generateToken(User user) {
        return jwtUtil.generateToken(user.getId().toString());
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
}
