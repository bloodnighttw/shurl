package dev.bntw.shurl.utils.JwtAuth;

import dev.bntw.shurl.exception.jwt.InvalidTokenException;
import dev.bntw.shurl.persistence.entity.User;
import dev.bntw.shurl.services.JwtService;
import jakarta.annotation.Nullable;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class OptionalAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private static final String BEARER_PREFIX = "Bearer ";

    private final JwtService jwtService;

    @Autowired
    public OptionalAuthArgumentResolver(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OptionalJwtAuth.class);
    }

    @Override
    @Nullable
    public User resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        var authorizationHeader = webRequest.getHeader("Authorization");

        if(authorizationHeader == null){
            return null;
        }

        if(!authorizationHeader.startsWith(BEARER_PREFIX)){
            throw new InvalidTokenException("Invalid token prefix");
        }

        String token = authorizationHeader.substring(BEARER_PREFIX.length());

        return jwtService.parseToken(token);
    }
}
