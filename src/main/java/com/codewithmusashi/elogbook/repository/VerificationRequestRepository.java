package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.VerificationRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {
    List<VerificationRequest> findByUserId(Long userId);
}