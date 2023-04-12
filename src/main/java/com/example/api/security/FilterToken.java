package com.example.api.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.api.repositories.UserRepository;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterToken extends OncePerRequestFilter{

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String token;

            var authorizationHeader = request.getHeader("Authorization");
            if(authorizationHeader != null){
                token = authorizationHeader.replace("Bearer ", "");
                var subject = this.tokenService.getSubject(token);
    
                var usuario = this.userRepository.findByLogin(subject);
    
                var authentication = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
    
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        } catch (TokenExpiredException e) {

        } catch (Exception e) {
            response.setStatus(401);
            return;
        }
        
        filterChain.doFilter(request, response);
    }

}
