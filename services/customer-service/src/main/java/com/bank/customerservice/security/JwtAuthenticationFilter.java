package com.bank.customerservice.security;

import com.bank.customerservice.util.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

import java.io.IOException;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        String requestPath = request.getServletPath();

        // Allow H2 Console
        if (requestPath.startsWith("/h2-console")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Read Authorization Header
        final String authHeader =
                request.getHeader("Authorization");

        // If header missing or invalid
        if (authHeader == null
                || !authHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        // Extract JWT token
        final String jwtToken = authHeader.substring(7);

        String email = null;

        try {

            // Extract email from token
            email = jwtService.extractUsername(jwtToken);

        } catch (Exception ex) {

            filterChain.doFilter(request, response);
            return;
        }

        // Authenticate only if not already authenticated
        if (email != null
                && SecurityContextHolder.getContext()
                .getAuthentication() == null) {

            // Validate token
            if (jwtService.validateToken(jwtToken)) {

                String role = jwtService.extractRole(jwtToken);

                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "ROLE_" + role
                                        )
                                )
                        );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                // Store authentication
                SecurityContextHolder.getContext()
                        .setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}