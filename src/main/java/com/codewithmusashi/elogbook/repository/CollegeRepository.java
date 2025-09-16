package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CollegeRepository extends JpaRepository<College, Long> {
    Optional<College> findByName(String name);
    Optional<College> findByContactEmail(String contactEmail);
}