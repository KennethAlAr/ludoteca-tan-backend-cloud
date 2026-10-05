package com.ccsw.tutorialauthor.common.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    // De momento la clave la creamos al arrancar, pero se puede guardar de forma persistente.
    private final SecretKey key = Keys.hmacShaKeyFor("123456789123456789123456789123456789".getBytes(StandardCharsets.UTF_8));

    public Claims getClaims(String token) {
        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
    }

    public void validateToken(String authorizationHeader) {
        if (authorizationHeader == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Token no proporcionado.");
        }

        String[] parts = authorizationHeader.split(" ");

        String prefix = parts[0];
        String token = parts[1];

        if (!"Bearer".equals(prefix)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Formato de token no válido.");
        }

        String role = getClaims(token).get("role", String.class);

        if (!"ADMIN".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes autorización para llevar a cabo esa acción.");
        }
    }
}
