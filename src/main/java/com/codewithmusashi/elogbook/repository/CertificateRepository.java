package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CertificateRepository extends JpaRepository<Certificate, Long> {
    List<Certificate> findByStudent_Id(Long studentId);
    List<Certificate> findByDepartment_Id(Long departmentId);
}
