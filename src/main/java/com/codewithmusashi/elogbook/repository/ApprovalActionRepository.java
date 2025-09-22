package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.ApprovalAction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalActionRepository extends JpaRepository<ApprovalAction, Long> {}