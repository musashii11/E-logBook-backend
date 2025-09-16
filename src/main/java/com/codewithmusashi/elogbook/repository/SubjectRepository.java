package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, Long> {
    List<Subject> findByCourseId(Long courseId);
}