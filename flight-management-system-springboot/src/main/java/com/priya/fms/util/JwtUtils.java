package com.priya.fms.util;

import com.priya.fms.common.AccessDeniedException;
import com.priya.fms.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static String secret = "secret";
    private static long expiryDuration = 60 * 60;

    public String generateJwt(UserEntity user) {

        long milliTime = System.currentTimeMillis();
        long expiryTime = milliTime + expiryDuration * 1000;

        Date issuedAt = new Date(milliTime);
        Date expiryAt = new Date(expiryTime);

        Claims claims = Jwts.claims()
                .setIssuer(user.getUserId().toString())
                .setIssuedAt(issuedAt)
                .setExpiration(expiryAt);

        claims.put("type", user.getRole());
        claims.put("name", user.getName());
        claims.put("emailId", user.getEmail());

        return Jwts.builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims verify(String authorization) throws Exception {
        try {
            Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authorization).getBody();
            return claims;
        } catch (Exception e) {
            throw new AccessDeniedException("Access Denied");
        }
    }
}
