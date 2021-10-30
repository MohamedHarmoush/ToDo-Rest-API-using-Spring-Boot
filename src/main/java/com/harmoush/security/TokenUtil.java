package com.harmoush.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class TokenUtil {

    private final String CLAIMS_SUBJECT = "sub";
    private final String CLAIMS_ISSUED_AT = "iat";

    @Value("${auth.expiration}")
    private Long TOKEN_EXPIRATION = 604800L; // expired after 7 days
    @Value("${auth.secret}")
    private String SECRET;

    public String generateToken(UserDetails userDetails) {
        //To generate JWT we need
        //claims
        //expiration
        //sign
        //compact
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIMS_SUBJECT, userDetails.getUsername());
        claims.put(CLAIMS_ISSUED_AT, new Date());

        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateTokenExpirationDate())
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private Date generateTokenExpirationDate() {
        return new Date(System.currentTimeMillis() + (TOKEN_EXPIRATION * 1000));
    }

    public String getUsernameFromToken(String token) {
        Claims claims = getClaims(token);
        if (claims == null) return null;
        return claims.getSubject();
    }

    private Claims getClaims(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception ex) {
            claims = null;
        }
        return claims;
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        Claims claims = getClaims(token);
        if (claims == null) return false;
        return claims.getExpiration().before(new Date());
    }
}
