package com.codewithmusashi.elogbook.controller;

import com.codewithmusashi.elogbook.entity.Department;
import com.codewithmusashi.elogbook.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    @Autowired
    private DepartmentRepository departmentRepository;

    // Create a department (HOD/Admin could call this)
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Department dept) {
        if (dept.getName() == null || dept.getName().isBlank()) {
            return ResponseEntity.badRequest().body("Department name is required");
        }
        Department saved = departmentRepository.save(dept);
        return ResponseEntity.ok(saved);
    }

    @GetMapping
    public ResponseEntity<List<Department>> list() {
        return ResponseEntity.ok(departmentRepository.findAll());
    }

    // Get one department
    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        return departmentRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
