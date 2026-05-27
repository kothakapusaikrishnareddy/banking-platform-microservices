package com.bank.transactionservice.security;


import com.bank.transactionservice.util.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService  jwtService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String requestPath = request.getServletPath();

        if(requestPath.startsWith("/h2-console")){
            filterChain.doFilter(request,response);

            return ;
        }

        final String authHeader = request.getHeader("Authorization");

        if(authHeader == null && !authHeader.startsWith("Bearer ")){

            filterChain.doFilter(request,response);

            return ;
        }

        final String jwtToken = authHeader.substring(7);

        String email;

        try{
            email=jwtService.extractUsername(jwtToken);
        }
        catch(Exception e){
            filterChain.doFilter(request,response);
            return ;
        }

        if(email!=null && SecurityContextHolder.getContext().getAuthentication()==null){

            if(jwtService.validateToken(jwtToken)){

                String role = jwtService.extrcatRole(jwtToken);
                UsernamePasswordAuthenticationToken authToken =
                        new  UsernamePasswordAuthenticationToken(
                                email,
                                null,
                                List.of(
                                        new SimpleGrantedAuthority(
                                                "Role_"+role
                                        )
                                )
                        );
                authToken.setDetails(
                        new WebAuthenticationDetailsSource()
                                .buildDetails(request)
                );

                SecurityContextHolder
                        .getContext()
                        .setAuthentication(authToken);

            }
        }

        filterChain.doFilter(request,response);



    }


}
