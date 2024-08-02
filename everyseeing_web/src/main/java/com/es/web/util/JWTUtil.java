package com.es.web.util;

import java.security.Key;

import io.jsonwebtoken.security.Keys;


public class JWTUtil {

	
	/**
	 * secret key 설정
	 * @param key 비밀키 
	 * @return 키 
	 * @throws Exception 에러
	 */
	public Key createKey(String key) throws Exception {
    	return Keys.hmacShaKeyFor(key.getBytes("UTF-8"));
    }
}
