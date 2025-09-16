package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    List<AuditLog> findByTableName(String tableName);
    List<AuditLog> findByUserId(Long userId);
}