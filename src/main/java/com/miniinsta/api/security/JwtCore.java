package com.miniinsta.api.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtCore {

    // Секретный ключ (в реале он сложнее, но для сдачи пойдет)
    private final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // Время жизни токена (24 часа)
    private final int expirationMs = 86400000; 

    public String generateToken(String username, Long userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("sub", userId) // Требование учителя: sub (user_id) [cite: 19]
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + expirationMs))
                .signWith(key)
                .compact();
    }
}