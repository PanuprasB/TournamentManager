package com.SBTM.TournamentManager.Security;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

    @Component
    public class JwtUtil {

        private String secretKey = "raktas";

        public String generateToken(String username) {
            return Jwts.builder()
                    .setSubject(username)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + 3600 * 1000))
                    .signWith(SignatureAlgorithm.HS256, secretKey)
                    .compact();
        }

        public boolean validateToken(String token) {
            try {
                Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        public String extractUsername(String token) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
        }

    }
