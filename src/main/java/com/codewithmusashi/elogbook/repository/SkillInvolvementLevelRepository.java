package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.SkillInvolvementLevel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillInvolvementLevelRepository extends JpaRepository<SkillInvolvementLevel, Long> {
    List<SkillInvolvementLevel> findBySkillId(Long skillId);
}