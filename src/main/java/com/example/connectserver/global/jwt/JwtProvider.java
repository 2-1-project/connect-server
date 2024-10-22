package com.example.connectserver.global.jwt;

import com.example.connectserver.domain.user.dto.CustomUserDetails;
import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {

    private SecretKey secretKey;
    private final JwtProperties jwtProperties;
    private static final long ACCESS_TOKEN_VALIDITY_SECONDS = 1000 * 60 * 60 ;

    @PostConstruct
    public void setSecretKey(){
        secretKey = new SecretKeySpec(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8), Jwts.SIG.HS256.key().build().getAlgorithm());
    }


    public String TokenGenerator(CustomUserDetails userDetails) {
        return Jwts.builder()
                .claim("username",userDetails.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis()+ACCESS_TOKEN_VALIDITY_SECONDS))
                .signWith(secretKey)
                .compact();

    }


    public boolean isExpired(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getExpiration().before(new Date());
    }



}
