package com.es.web.util;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

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
	
	/**
	 * 랜덤 문자열 생성
	 * @param len
	 * @return
	 * @throws Exception
	 */
	public static String makeRandStr(int len) throws Exception {
		if(len > 0) {
			StringBuffer sb = new StringBuffer(len);
			
			for(int i=0; i<len; i++) {
				int type = (int) (Math.random() * 3);
				
				double random = Math.random();
				
				switch(type) {
				case 0:
					sb.append((int)(random * 9 + 1));
					break;
				case 1:
					sb.append((char)(random * 26 + 'A'));
					break;
				default: 
					sb.append((char)(random * 26 + 'a'));
					break;
				}
			}
			
			return sb.toString();
		} else {
			return "";
		}
	}
	
	/**
	 * Map null 체크
	 * @param param
	 * @return
	 */
	public static boolean checkIsNull(Map<String, Object> param) {
		if(param == null || param.isEmpty()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * 파라미터 null 체크
	 * @param param
	 * @param key
	 * @return
	 */
	public static boolean checkIsNull(Map<String, Object> param, String key) {
		if(param == null || key == null || (param.get(key) == null) || param.get(key).equals("")) {
			return true;
		}

		return false;
	}
}
