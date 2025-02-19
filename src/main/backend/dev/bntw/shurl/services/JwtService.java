package dev.bntw.shurl.services;
import dev.bntw.shurl.exception.jwt.InvalidTokenException;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.persistence.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Service
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
                .add("password", userDetails.getHashPassword())
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
        } catch (JwtException e) {
            throw new InvalidTokenException(e.getMessage());
        }

        var username = claims.get("username", String.class);
        var password = claims.get("password", String.class);
        var email = claims.get("email", String.class);

        var user = userRepository.findByUsernameAndEmailAndHashPassword(username, email, password);

        if(user != null) {
            return user;
        }

        throw new InvalidTokenException("Invalid token");
    }
}
