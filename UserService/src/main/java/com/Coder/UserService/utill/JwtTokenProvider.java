package com.Coder.UserService.utill;

import com.Coder.UserService.config.JwtConfig;
import com.Coder.UserService.model.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {

    private final JwtConfig jwtConfig;
    private Key key;

    public JwtTokenProvider(JwtConfig jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    @PostConstruct
    public void init() {
        byte[] secretBytes = jwtConfig.getSecret().getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(secretBytes);
    }

    /**
     * Generate token with username, email, and roles
     */
    public String generateToken(UserDetailsImpl userDetails) {
        long now = System.currentTimeMillis();
        Date issuedAt = new Date(now);
        Date expiryDate = new Date(now + jwtConfig.getExpirationMs());

        // Extract roles
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority) // e.g., ROLE_ADMIN, ROLE_USER
                .collect(Collectors.toList());

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", userDetails.getUser().getEmail());
        claims.put("roles", roles);

        return Jwts.builder()
                .setSubject(userDetails.getUsername()) // subject = username/email/phone
                .addClaims(claims)
                .setIssuedAt(issuedAt)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getSubjectFromToken(String token) {
        return getClaims(token).getSubject();
    }

    public String getUsername(String token) {
        return getClaims(token).getSubject(); // subject = username
    }

    public String getEmail(String token) {
        return getClaims(token).get("email", String.class);
    }

    public List<String> getRoles(String token) {
        Claims claims = getClaims(token);
        return claims.get("roles", List.class); // safe cast
    }


    public Date getExpiryDate(String token) {
        return getClaims(token).getExpiration();
    }
}
