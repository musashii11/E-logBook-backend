// src/main/java/com/codewithmusashi/elogbook/security/CustomUserDetailsService.java
package com.codewithmusashi.elogbook.security;

import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User u = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

        if (u.getApprovalStatus() != User.ApprovalStatus.APPROVED ||
                u.getActiveStatus()   != User.ActiveStatus.ACTIVE) {
            throw new UsernameNotFoundException("User not approved/active: " + username);
        }

        var auth = new SimpleGrantedAuthority("ROLE_" + u.getRole().name());
        return new org.springframework.security.core.userdetails.User(
                u.getUsername(), u.getPassword(), Collections.singletonList(auth));
    }
}
