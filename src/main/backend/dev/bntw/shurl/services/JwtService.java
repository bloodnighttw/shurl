package dev.bntw.shurl.services;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.utils.MemberDetail;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
public class JwtService {

    private final SecretKey secretKey;
    private final int validSeconds;


    public JwtService(
            @Value("${shuri.jwt.secret}") String secretKey,
            @Value("${shuri.jwt.valid-seconds}") int validSeconds) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validSeconds = validSeconds;
    }

    public String createToken(User userDetails) {

        long expireMillis = Instant.now()
                .plusSeconds(validSeconds)
                .getEpochSecond() * 1000;

        var claims = Jwts.claims()
                .issuedAt(new Date())
                .expiration(new Date(expireMillis))
                .add("username", userDetails.getUsername())
                .add("email", userDetails.getEmail())
                .build();

        return Jwts.builder()
                .claims(claims)
                .signWith(secretKey)
                .compact();
    }

}
