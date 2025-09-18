// src/main/java/com/codewithmusashi/elogbook/security/JwtAuthenticationFilter.java
package com.codewithmusashi.elogbook.security;

import com.codewithmusashi.elogbook.entity.User;
import com.codewithmusashi.elogbook.repository.UserRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired private UserDetailsService userDetailsService;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // Public routes (no token required)
        if (path.startsWith("/api/auth/")
                || path.equals("/actuator/health")) {
            chain.doFilter(request, response);
            return;
        }

        // If already authenticated, continue
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            chain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring(7);
            try {
                Claims claims = jwtUtil.parseToken(token);
                Long userId = Long.parseLong(claims.getSubject());

                Optional<User> userOpt = userRepository.findById(userId);
                if (userOpt.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not found");
                    return;
                }

                User user = userOpt.get();

                // New checks: must be APPROVED + ACTIVE to authenticate
                if (user.getApprovalStatus() != User.ApprovalStatus.APPROVED
                        || user.getActiveStatus() != User.ActiveStatus.ACTIVE) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "User not approved/active");
                    return;
                }

                // Load authorities and set SecurityContext
                UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception e) {
                // signature/expired/parse/etc.
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
                return;
            }
        }

        // If no token header, just continue; SecurityConfig will block protected routes
        chain.doFilter(request, response);
    }
}
