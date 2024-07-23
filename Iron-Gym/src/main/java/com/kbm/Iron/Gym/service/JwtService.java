package com.kbm.Iron.Gym.service;

import com.kbm.Iron.Gym.entity.Admin;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String SECRET_KEY = "526cf9e9bd7fd85a418f6669bd62e9d8089dcef869d79574df65223978f89ec7";

    //validate token
    public boolean isValid(String token, UserDetails user){
        String username = extractUsername(token);
        return (username.equals(user.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token){
        return (Date) extractClaim(token, Claims::getExpiration);
    }

    //extract username from token
    public String extractUsername(String token){
        return extractClaim(token, Claims::getSubject);
    }

    //extract a given claim from token
    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims = extractClaims(token);
        return resolver.apply(claims);
    }

    //extract all claims from token
    private Claims extractClaims(String token){
        return Jwts
                .parser()
                .verifyWith(getSignKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    //generate token for a user
    public String generateToken(Admin admin){
        String token = Jwts
                .builder()
                .subject(admin.getUsername())
                .claim("id",admin.getId())
                .claim("role",admin.getRole())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 24*60*60*1000))
                .signWith(getSignKey())
                .compact();

        return token;
    }

    private SecretKey getSignKey(){
        byte[] keyBytes = Decoders.BASE64URL.decode(SECRET_KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
