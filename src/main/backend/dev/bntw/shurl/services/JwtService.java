package dev.bntw.shurl.services;
import dev.bntw.shurl.exception.jwt.InvalidTokenException;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.persistence.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtService {

    private final SecretKey secretKey;
    private final int validSeconds;

    private final JwtParser jwtParser;

    private final UserRepository userRepository;



    @Autowired
    public JwtService(
            @Value("${shuri.jwt.secret}") String secretKey,
            @Value("${shuri.jwt.valid-seconds}") int validSeconds, UserRepository userRepository) {
        this.secretKey = Keys.hmacShaKeyFor(secretKey.getBytes());
        this.validSeconds = validSeconds;
        this.userRepository = userRepository;
        this.jwtParser = Jwts.parser().verifyWith(this.secretKey).build();
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

    public User parseToken(String token) throws InvalidTokenException {
        Claims claims;
        try {
            claims =  jwtParser.parseSignedClaims(token).getPayload();
        } catch (ExpiredJwtException e) {
            throw new InvalidTokenException("Token expired");
        } catch (JwtException e){
            log.error("Invalid token", e);
            throw new InvalidTokenException("JWT error");
        }

        var username = claims.get("username", String.class);
        var email = claims.get("email", String.class);

        var user = userRepository.findUserByUsernameAndEmail(username, email);

        if(user != null) {
            return user;
        }

        throw new InvalidTokenException("Invalid token");
    }
}
