package dev.bntw.shurl.utils.JwtAuth;

import dev.bntw.shurl.exception.jwt.InvalidTokenException;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.services.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Objects;

@Aspect
@Component
public class JwtAuthAspect{

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    @Autowired
    public JwtAuthAspect(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Before("@annotation(dev.bntw.shurl.utils.JwtAuth.Auth)")
    protected void auth() throws InvalidTokenException{

        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();

        String authorizationHeader = request.getHeader("Authorization");

        if(authorizationHeader == null){
            throw new InvalidTokenException("Authorization header not found");
        }

        if(!authorizationHeader.startsWith(BEARER_PREFIX)){
            throw new InvalidTokenException("Invalid token prefix");
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());

        User memberDetails = jwtService.parseToken(token);

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                memberDetails, null, memberDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);

    }
}
