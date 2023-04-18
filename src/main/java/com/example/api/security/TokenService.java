package com.example.api.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.api.entities.Usuario;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class TokenService {

    private static final String SECRET_KEY = "753778214125442A472D4B6150645267556B58703273357638792F423F452848";
    
    private Key get_key(){
        byte[] key = Decoders.BASE64.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(key);
    }

    // public String gerarToken(Usuario usuario) {
    //     return JWT.create().withIssuer("Pais").withSubject(usuario.getUsername()).withClaim("id", usuario.getId())
    //         .withExpiresAt(Date.from(Instant.now().plus(300, ChronoUnit.SECONDS))).sign(Algorithm.HMAC256("secret"));
        
    // }

    // public String getSubject(String token) throws TokenExpiredException, Exception{
    //     return JWT.require(Algorithm.HMAC256("secret")).withIssuer("Pais").build().verify(token).getSubject();
    // }

    private Claims get_claims(String token) {
        return Jwts.parserBuilder().setSigningKey(get_key()).build().parseClaimsJws(token).getBody();
    }

    public String get_subject(String token){
        return get_claims(token).getSubject();
    }

    public String gerar_token(Usuario usuario) {
        return Jwts
                .builder()
                .setSubject(usuario.getLogin())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 300000))
                .signWith(get_key(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, Usuario usuario){
        final String subject = get_subject(token);
        return subject.equals(usuario.getLogin()) && !isTokenExpired(token);
    }

    public boolean isTokenExpired(String token) {
        return get_claims(token).getExpiration().before(new Date());

    }
}
