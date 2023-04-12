package com.example.api.security;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.api.entities.Usuario;

@Service
public class TokenService {
    
    public String gerarToken(Usuario usuario) {
        return JWT.create().withIssuer("Pais").withSubject(usuario.getUsername()).withClaim("id", usuario.getId())
            .withExpiresAt(Date.from(Instant.now().plus(300, ChronoUnit.SECONDS))).sign(Algorithm.HMAC256("secret"));
        
    }

    public String getSubject(String token) throws TokenExpiredException, Exception{
        return JWT.require(Algorithm.HMAC256("secret")).withIssuer("Pais").build().verify(token).getSubject();
    }

}
