package com.es.web.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.es.web.interceptor.CustomInterceptor;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

	@Bean
	public CustomArgumentResolver customArgumentResolver() {
		return new CustomArgumentResolver();
	}
	
	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
		resolvers.add(customArgumentResolver());
	}
	
	@Bean
    public CustomInterceptor interceptor(){
        return new CustomInterceptor();
    }
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(interceptor())
		.excludePathPatterns("/assets/**", "/js/**", "/css/**", "/favicon.ico")
		;
	}
}
