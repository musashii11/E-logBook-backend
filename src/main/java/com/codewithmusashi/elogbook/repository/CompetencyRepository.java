package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Competency;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CompetencyRepository extends JpaRepository<Competency, Long> {
    List<Competency> findByDepartmentId(Long departmentId);
}