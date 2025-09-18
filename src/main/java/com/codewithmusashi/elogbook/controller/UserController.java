// src/main/java/com/codewithmusashi/elogbook/controller/UserController.java
package com.codewithmusashi.elogbook.controller;

import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.repository.UserRepository;
import com.codewithmusashi.elogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired private UserService userService;
    @Autowired private UserRepository userRepository;

    // Create any user (HOD/TEACHER/STUDENT) via one endpoint.
    // Creator must be HOD or TEACHER (teacher can only create students).
    @PostMapping
    public ResponseEntity<?> create(@AuthenticationPrincipal UserDetails principal,
                                    @RequestBody User toCreate) {
        User creator = null;
        if (principal != null) {
            creator = userRepository.findByUsername(principal.getUsername()).orElse(null);
        }
        User saved = userService.createUser(creator, toCreate);
        return ResponseEntity.ok(saved);
    }

    // List PENDING users in my department (HOD/TEACHER)
    @GetMapping("/pending")
    public ResponseEntity<?> pending(@AuthenticationPrincipal UserDetails principal) {
        User me = userRepository.findByUsername(principal.getUsername()).orElseThrow();
        if (me.getDepartment() == null) return ResponseEntity.badRequest().body("No department");
        var list = userRepository.findByDepartmentAndApprovalStatus(me.getDepartment(), User.ApprovalStatus.PENDING);
        return ResponseEntity.ok(list);
    }

    // Approve user (HOD/TEACHER)
    @PostMapping("/{id}/approve")
    public ResponseEntity<?> approve(@AuthenticationPrincipal UserDetails principal, @PathVariable Long id) {
        User approver = userRepository.findByUsername(principal.getUsername()).orElseThrow();
        User updated = userService.approve(approver, id);
        return ResponseEntity.ok(updated);
    }

    // Reject user (HOD/TEACHER)
    @PostMapping("/{id}/reject")
    public ResponseEntity<?> reject(@AuthenticationPrincipal UserDetails principal,
                                    @PathVariable Long id,
                                    @RequestBody(required = false) Map<String, String> body) {
        User approver = userRepository.findByUsername(principal.getUsername()).orElseThrow();
        String remark = body != null ? body.getOrDefault("remark", null) : null;
        User updated = userService.reject(approver, id, remark);
        return ResponseEntity.ok(updated);
    }

    // Toggle active/inactive
    // HOD can toggle anyone; TEACHER can toggle only students.
    @PatchMapping("/{id}/active")
    public ResponseEntity<?> setActive(@AuthenticationPrincipal UserDetails principal,
                                       @PathVariable Long id,
                                       @RequestBody Map<String, Boolean> body) {
        if (body == null || !body.containsKey("active")) {
            return ResponseEntity.badRequest().body("Body must have {\"active\": true/false}");
        }
        boolean active = body.get("active");
        User actor = userRepository.findByUsername(principal.getUsername()).orElseThrow();
        User updated = userService.setActive(actor, id, active);
        return ResponseEntity.ok(updated);
    }
}
