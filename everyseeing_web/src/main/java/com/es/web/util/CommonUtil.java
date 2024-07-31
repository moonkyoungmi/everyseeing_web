package com.es.web.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.google.common.io.BaseEncoding;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.security.SecureRandom;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	/**
	 * json 객체를 map 객체로 변환
	 * @param json
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static Map<String, Object> convertJsonToMap(String json) {
		Map<String, Object> resultMap = new HashMap<>();
		
		try {
			ObjectMapper mapper = new ObjectMapper();
			resultMap = mapper.readValue(json, Map.class);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return resultMap;
	}
	
	/**
	 * AES256 암호화용 시크릿 키 생성
	 * @return
	 */
	public static String makeSecKey() {
		byte[] key = new byte[32];
	    new SecureRandom().nextBytes(key);
	    String encryptionKey = Base64.getEncoder().encodeToString(key);

	    return encryptionKey;
	}
	
	/**
	 * 시크릿 키 가져오기
	 * @param request
	 * @return
	 */
	public static String getSecKey(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		return (String) session.getAttribute("secret_key");
	}
}
