package com.codewithmusashi.elogbook.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class healthController {
    @GetMapping("/health")
    public String healthCheck() {
        return "e-logbook is up and running! Time: " + java.time.LocalDateTime.now();
    }
}
