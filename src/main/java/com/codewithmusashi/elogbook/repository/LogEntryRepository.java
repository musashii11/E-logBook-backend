package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.LogEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogEntryRepository extends JpaRepository<LogEntry, Long> {
    List<LogEntry> findByStudent_Id(Long studentId);
    List<LogEntry> findByDepartment_Id(Long departmentId);

}
