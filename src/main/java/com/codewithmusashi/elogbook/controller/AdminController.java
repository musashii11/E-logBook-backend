package com.codewithmusashi.elogbook.controller;

import com.codewithmusashi.elogbook.entity.College;
import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.service.CollegeService;
import com.codewithmusashi.elogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private CollegeService collegeService;

    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (user.getCollege() == null || user.getCollege().getId() == null) {
            return ResponseEntity.badRequest().body("College ID is required");
        }
        User savedUser = userService.registerUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @PostMapping("/colleges")
    public ResponseEntity<?> createCollege(@RequestBody College college) {
        College savedCollege = collegeService.createCollege(college);
        return ResponseEntity.ok(savedCollege);
    }
}