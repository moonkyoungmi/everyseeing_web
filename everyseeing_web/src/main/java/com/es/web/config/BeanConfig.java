package com.es.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.es.web.util.JWTUtil;

@Configuration
@PropertySource(value= {"classpath:/properties/common.properties"}, encoding = "UTF-8")
public class BeanConfig {

	@Value("${jwt.secret.key}")
	private String SECRET_KEY;
	
	@Bean(name="jwtUtil")
	public JWTUtil jwt() {
		return new JWTUtil(SECRET_KEY);
	}
}
