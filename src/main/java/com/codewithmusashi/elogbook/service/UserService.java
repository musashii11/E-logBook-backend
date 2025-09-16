package com.codewithmusashi.elogbook.service;

import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.repository.UserRepository;
import com.codewithmusashi.elogbook.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    public User registerUser(User user) {
        if (user.getCollege() == null || user.getCollege().getId() == null) {
            throw new IllegalArgumentException("College ID is required");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setStatus(User.Status.PENDING);
        user.setCreatedAt(LocalDateTime.now());
        user.setUpdatedAt(LocalDateTime.now());
        return userRepository.save(user);
    }

    public Optional<User> authenticateUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
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