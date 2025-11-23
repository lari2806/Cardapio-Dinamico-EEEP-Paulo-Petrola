package br.com.cardapio.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {

    // Chave fixa — mínimo 32 bytes!
    private final SecretKey secretKey = Keys.hmacShaKeyFor(
            "CHAVE_SUPER_SECRETA_DE_32_BYTES_AQUI_1234567890".getBytes()
    );

    private final long EXPIRATION_MS = 1000 * 60 * 60; // 1 hora

    // ===========================
    // TOKEN: CRIAÇÃO
    // ===========================
    public String generateToken(br.com.cardapio.model.User user) {
        return Jwts.builder()
                .setSubject(user.getEmail())
                .claim("role", user.getRole()) // se existir
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_MS))
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    // ===========================
    // TOKEN: EXTRAÇÃO
    // ===========================
    private Claims getAllClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String extractEmail(String token) {
        return getAllClaims(token).getSubject();
    }

    public String extractRole(String token) {
        return getAllClaims(token).get("role", String.class);
    }

    // ===========================
    // TOKEN: VALIDAÇÃO
    // ===========================
    public boolean isValidToken(String token) {
        try {
            Claims claims = getAllClaims(token);
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}
