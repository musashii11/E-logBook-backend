// src/main/java/com/codewithmusashi/elogbook/repository/UserRepository.java
package com.codewithmusashi.elogbook.repository;

import com.codewithmusashi.elogbook.entity.Department;
import com.codewithmusashi.elogbook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    Optional<User> findByRollNumber(String rollNumber);

    List<User> findByDepartmentAndRoleIn(Department dept, List<User.Role> roles);
    List<User> findByDepartmentAndApprovalStatus(Department dept, User.ApprovalStatus status);
}
