package application.config;

import application.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;

@Configuration
public class AuthConfig implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor authInterceptor;

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authInterceptor).excludePathPatterns(Arrays.asList("/user/login", "/user/add","/user/login/", "/user/add/"));
    }
}