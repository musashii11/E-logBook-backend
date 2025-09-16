package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.SystemSetting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SystemSettingRepository extends JpaRepository<SystemSetting, Long> {
    Optional<SystemSetting> findByKey(String key);
}









