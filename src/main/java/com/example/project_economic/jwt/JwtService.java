package com.example.project_economic.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtService {
    public String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";
    public String generateToken(String username) {
        Date date=new Date();
        Date expiryDate=new Date(date.getTime()+60*60*2*1000);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256,getSignKey())
                .compact();
    }

    private Key getSignKey() {
        byte[] keybytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keybytes);
    }

    public String extractUsernameFromToken(String token) {
        Claims claims=Jwts.parser()
                .setSigningKey(getSignKey())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String username=this.extractUsernameFromToken(token);
        return username.equals(userDetails.getUsername());
    }
}
