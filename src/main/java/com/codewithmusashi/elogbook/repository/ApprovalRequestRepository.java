package com.codewithmusashi.elogbook.repository;


import com.codewithmusashi.elogbook.entity.ApprovalRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApprovalRequestRepository extends JpaRepository<ApprovalRequest, Long> {

    Optional<ApprovalRequest> findByModuleAndModuleIdAndStatus(
            ApprovalRequest.Module module,
            Long moduleId,
            ApprovalRequest.Status status
    );

    List<ApprovalRequest> findByModuleAndStatus(ApprovalRequest.Module module, ApprovalRequest.Status status);
}