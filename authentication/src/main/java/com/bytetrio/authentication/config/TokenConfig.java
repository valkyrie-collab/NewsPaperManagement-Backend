package com.bytetrio.authentication.config;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class TokenConfig {
    @Value("${jwts.security}")
    private String securityKey;

    private Key generateKey() {return Keys.hmacShaKeyFor(Decoders.BASE64.decode(securityKey));}

    private <I> I claims(String token, Function<Claims, I> claimsBearer) {
        return claimsBearer.apply(Jwts.parserBuilder().setSigningKey(generateKey()).build()
            .parseClaimsJws(token).getBody());
    }

    private boolean isNotExpired(String token) {
        return !claims(token, Claims::getExpiration).before(new Date());
    }

    public String generateToken(String username, Collection<? extends GrantedAuthority> authorities) {
        Map<String, Object> claim = new HashMap<>();
        List<String> roles = authorities.stream().map(GrantedAuthority::getAuthority).toList();
        claim.put("roles", roles);
        
        return Jwts.builder().setClaims(claim).setSubject(username)
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30 * 30))
            .signWith(generateKey()).compact();

    }

    public String getUsername(String token) {return claims(token, Claims::getSubject);}

    public boolean isValid(String token, UserDetails userDetails) {
        return userDetails.getUsername().equals(getUsername(token)) && isNotExpired(token);
    }

}
