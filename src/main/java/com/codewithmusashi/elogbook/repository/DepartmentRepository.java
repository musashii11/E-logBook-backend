package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
}
