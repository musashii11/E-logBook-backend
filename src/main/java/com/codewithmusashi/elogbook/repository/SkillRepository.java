package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Skill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkillRepository extends JpaRepository<Skill, Long> {
    List<Skill> findByCompetency_Id(Long competencyId);
}
