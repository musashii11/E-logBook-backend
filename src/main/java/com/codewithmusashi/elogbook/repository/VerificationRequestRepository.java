package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.VerificationRequest;
import com.codewithmusashi.elogbook.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VerificationRequestRepository extends JpaRepository<VerificationRequest, Long> {
    List<VerificationRequest> findByDepartmentAndStatus(Department department, VerificationRequest.Status status);
}
