package com.scratchbling.service;

import com.scratchbling.dto.TokenInfo;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtTokenService {

    @Autowired
    private UserDetailsService userDetailsService;

    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public TokenInfo getTokenFor(UserDetails userDetails) {

        List<String> permissionList = userDetails.getAuthorities().
                stream().
                map(GrantedAuthority::getAuthority).
                collect(Collectors.toList());

        Instant issued = Instant.now();
        Instant expiration = Instant.now().plus(60, ChronoUnit.MINUTES);

        String token = Jwts.
                builder().
                setSubject(userDetails.getUsername()).
                claim("permissions",permissionList).
                setIssuedAt(Date.from(issued)).
                setExpiration(Date.from(expiration)).
                signWith(key).
                compact();

        return new TokenInfo(userDetails.getUsername(), token, userDetails.getAuthorities(), expiration.toEpochMilli());
    }

    public UserDetails getUserDetailsFromToken(String token) {
        String username = Jwts.
                parserBuilder().
                setSigningKey(key).
                build().
                parseClaimsJws(token).
                getBody().
                getSubject();

        return userDetailsService.loadUserByUsername(username);
    }

}
