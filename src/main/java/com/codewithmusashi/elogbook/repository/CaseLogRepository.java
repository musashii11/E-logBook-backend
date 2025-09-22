package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.CaseLog;
import com.codewithmusashi.elogbook.entity.Department;
import com.codewithmusashi.elogbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface     CaseLogRepository extends JpaRepository<CaseLog, Long> {
    List<CaseLog> findByStudent(User student);
    List<CaseLog> findByDepartmentAndStatus(Department dept, CaseLog.Staus status);
}
