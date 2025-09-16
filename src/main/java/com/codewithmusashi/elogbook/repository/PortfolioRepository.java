package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    List<Portfolio> findByUploadedById(Long userId);
    List<Portfolio> findByLogEntryId(Long logEntryId);
}