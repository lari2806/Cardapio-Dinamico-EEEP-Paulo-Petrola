package br.com.cardapio.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Chave fixa — mínimo 32 bytes
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "CHAVE_SUPER_SECRETA_DE_32_BYTES_AQUI_1234567890".getBytes()
    );

    private final long EXPIRATION = 1000 * 60 * 60; // 1h

    // Agora aceita User
    public String generateToken(br.com.cardapio.model.User user) {
        return Jwts.builder()
                .setSubject(user.getEmail()) // email é seu identificador
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // Seu filtro usa extractEmail(), então agora o método existe
    public String extractEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isValidToken(String token) {
        try {
            extractEmail(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
