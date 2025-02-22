package dev.bntw.shurl;

import dev.bntw.shurl.utils.JwtAuth.OptionalAuthMethodArgumentResolver;
import dev.bntw.shurl.utils.JwtAuth.RequiredAuthMethodArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class BackendConfig implements WebMvcConfigurer {

    private final RequiredAuthMethodArgumentResolver authHandlerMethodArgumentResolver;
    private final OptionalAuthMethodArgumentResolver optionalAuthHandlerMethodArgumentResolver;

    @Autowired
    public BackendConfig(RequiredAuthMethodArgumentResolver authHandlerMethodArgumentResolver, OptionalAuthMethodArgumentResolver optionalAuthHandlerMethodArgumentResolver) {
        this.authHandlerMethodArgumentResolver = authHandlerMethodArgumentResolver;
        this.optionalAuthHandlerMethodArgumentResolver = optionalAuthHandlerMethodArgumentResolver;
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(authHandlerMethodArgumentResolver);
        resolvers.add(optionalAuthHandlerMethodArgumentResolver);
    }
}
