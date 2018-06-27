package shiftworker.community.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shiftworker.community.interceptor.JwtInterceptor;

import java.util.List;

/**
 * @author sangsik.kim
 */
@Configuration
@EnableJpaAuditing
public class DefaultConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor());
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(userHandlerMethodArgumentResolver());
    }

    @Bean
    public LoginUserHandlerMethodArgumentResolver userHandlerMethodArgumentResolver() {
        return new LoginUserHandlerMethodArgumentResolver();
    }

    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
}
