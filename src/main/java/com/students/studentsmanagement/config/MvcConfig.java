package com.students.studentsmanagement.config;

import com.students.studentsmanagement.filter.Interceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry){
        registry.addInterceptor(new Interceptor())
                .excludePathPatterns(
                        "/management/user/login"
                ).order(0);
    }
}
