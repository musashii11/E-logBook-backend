package com.codewithmusashi.elogbook.service;

import com.codewithmusashi.elogbook.entity.College;
import com.codewithmusashi.elogbook.repository.CollegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CollegeService {

    @Autowired
    private CollegeRepository collegeRepository;

    public College createCollege(College college) {
        college.setCreatedAt(LocalDateTime.now());
        college.setUpdatedAt(LocalDateTime.now());
        return collegeRepository.save(college);
    }

    public Optional<College> findById(Long id) {
        return collegeRepository.findById(id);
    }


}