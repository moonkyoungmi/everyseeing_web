package com.es.web.util;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;


public class JWTUtil {

	@Value("${jwt.issuer}")
	private String ISSUER;

	private String SECRET_KEY;

	public JWTUtil(String secretKey) {
		this.SECRET_KEY = secretKey;
	}
	
	/**
	 * secret key 설정
	 * @param key 비밀키 
	 * @return 키 
	 * @throws Exception 에러
	 */
	public Key createKey(String key) throws Exception {
    	return Keys.hmacShaKeyFor(key.getBytes("UTF-8"));
    }
	
	public String createToken(Map<String, Object> param) throws Exception {
		Date now = new Date();
		
		Map<String, Object> claims = new HashMap<>();
		claims.putAll(param);
		claims.put("iat", now);
		
		return Jwts.builder()
				.setHeaderParam(Header.TYPE, Header.JWT_TYPE)
				.setHeaderParam("alg", "HS256")
				.setIssuer(ISSUER)
				.setIssuedAt(now)
				.setClaims(claims)
				.signWith(createKey(SECRET_KEY), SignatureAlgorithm.HS256)
				.compact();
	}
}
