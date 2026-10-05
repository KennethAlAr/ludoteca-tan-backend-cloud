package com.ccsw.tutoriallogin.common.jwt;

import com.ccsw.tutoriallogin.role.model.Role;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;

@Service
public class JwtService {

    // De momento la clave la creamos al arrancar, pero se puede guardar de forma persistente.
    private final SecretKey key = Keys.hmacShaKeyFor("123456789123456789123456789123456789".getBytes(StandardCharsets.UTF_8));

    public String getToken(String name, Role role) {

        return Jwts.builder().subject(name).claim("role", role.getName()).signWith(key).compact();
    }
}
