package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Verification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    List<Verification> findByLogEntryId(Long logEntryId);
    List<Verification> findByTeacherId(Long teacherId);
}