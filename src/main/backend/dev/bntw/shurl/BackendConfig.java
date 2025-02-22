package dev.bntw.shurl;

import dev.bntw.shurl.utils.JwtAuth.OptionalAuthArgumentResolver;
import dev.bntw.shurl.utils.JwtAuth.RequiredAuthArgumentResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class BackendConfig implements WebMvcConfigurer {

    private final RequiredAuthArgumentResolver requiredAuthArgumentResolver;
    private final OptionalAuthArgumentResolver optionalAuthArgumentResolver;

    @Autowired
    public BackendConfig(RequiredAuthArgumentResolver authHandlerMethodArgumentResolver, OptionalAuthArgumentResolver optionalAuthHandlerMethodArgumentResolver) {
        this.requiredAuthArgumentResolver = authHandlerMethodArgumentResolver;
        this.optionalAuthArgumentResolver = optionalAuthHandlerMethodArgumentResolver;
    }


    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(requiredAuthArgumentResolver);
        resolvers.add(optionalAuthArgumentResolver);
    }
}
