package dev.bntw.shurl.utils.JwtAuth;

import dev.bntw.shurl.exception.jwt.InvalidTokenException;
import dev.bntw.shurl.persistence.entity.User;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class RequiredAuthArgumentResolver implements HandlerMethodArgumentResolver {

    private final OptionalAuthArgumentResolver optionalAuthArgumentResolver;

    @Autowired
    public RequiredAuthArgumentResolver(OptionalAuthArgumentResolver optionalAuthArgumentResolver) {
        this.optionalAuthArgumentResolver = optionalAuthArgumentResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequiredJwtAuth.class);
    }

    @Override
    @NonNull
    public User resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer,@NonNull NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        var user = optionalAuthArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);

        if(user == null){
            throw new InvalidTokenException("Authorization header is missing");
        }

        return user;
    }
}
