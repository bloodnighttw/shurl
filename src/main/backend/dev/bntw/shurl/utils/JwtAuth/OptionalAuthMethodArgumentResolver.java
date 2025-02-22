package dev.bntw.shurl.utils.JwtAuth;

import dev.bntw.shurl.persistence.entity.User;
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
public class OptionalAuthMethodArgumentResolver implements HandlerMethodArgumentResolver {

    private final RequiredAuthMethodArgumentResolver requiredAuthHandlerMethodArgumentResolver;

    @Autowired
    public OptionalAuthMethodArgumentResolver(RequiredAuthMethodArgumentResolver requiredAuthMethodArgumentResolver) {
        this.requiredAuthHandlerMethodArgumentResolver = requiredAuthMethodArgumentResolver;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(OptionalJwtAuth.class);
    }

    @Override
    @Nullable
    public User resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        if(webRequest.getHeader("Authorization") == null){
            return null;
        }

        return requiredAuthHandlerMethodArgumentResolver.resolveArgument(parameter, mavContainer, webRequest, binderFactory);
    }
}
