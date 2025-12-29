package com.Finance.demo.Security.jwt;


import com.Finance.demo.Security.User.CustomUserDetailsService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        // 1. Check the Header
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String userEmail;

        // If no header or doesn't start with "Bearer ", skip this filter
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 2. Extract Token
        jwtToken = authHeader.substring(7); // Remove "Bearer " prefix
        userEmail = jwtUtils.extractUsername(jwtToken); // Read email from token

        // 3. Validation Logic
        // If user is present in token AND not already authenticated in this session
        if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // Load user from DB
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);

            // If token is valid for this user
            if (jwtUtils.validateToken(jwtToken, userDetails)) {

                // Create an Authentication Token (Standard Spring Security Object)
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                // Add details (like IP address, Session ID) to the token
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Update Security Context (The "Green Light")
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        // Continue the filter chain
        filterChain.doFilter(request, response);
    }
}