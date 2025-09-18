// src/main/java/com/codewithmusashi/elogbook/controller/AuthController.java
package com.codewithmusashi.elogbook.controller;

import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String username = body.get("username");
        String password = body.get("password");
        if (username == null || password == null) {
            return ResponseEntity.badRequest().body("Username and password are required");
        }
        var userOpt = userService.authenticateUser(username, password);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(401).body("Invalid credentials or not approved/active");
        }
        User user = userOpt.get();
        String token = userService.generateToken(user);

        Map<String, Object> resp = new HashMap<>();
        resp.put("token", token);
        resp.put("userId", user.getId());
        resp.put("role", user.getRole().name());
        return ResponseEntity.ok(resp);
    }
}
