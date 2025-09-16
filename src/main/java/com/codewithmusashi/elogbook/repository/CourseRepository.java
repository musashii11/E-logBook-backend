package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course, Long> {
}