package com.es.web.util;

import java.util.Optional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CookieUtil {

	/**
	 * 쿠키 가져오기
	 * @param request
	 * @param name
	 * @return
	 */
	public static Optional<Cookie> getCookie(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return Optional.of(cookie);
                }
            }
        }

        return Optional.empty();
    }
	
	/**
	 * 쿠키 설정
	 * @param name
	 * @param value
	 * @param second
	 * @param isHttpOnly
	 * @param domain
	 * @return
	 */
	private static Cookie setCookie(String name, String value, int second, boolean isHttpOnly, String domain) {
    	Cookie cookie = new Cookie(name, value);
    	cookie.setPath("/");
    	
    	if(domain != null) {
    		cookie.setDomain(domain);
    	}
    	
    	cookie.setHttpOnly(isHttpOnly);
    	
    	if(second > 0) {
    		cookie.setMaxAge(second);
    	}
    	
    	return cookie;
    }
	
	/**
     * 쿠키 추가
     * @param request
     * @param response
     * @param name 쿠키 이름
     * @param value 쿠키 값
     * @param second 시간
     * @param isHttpOnly httponly 여부
     */
    public static void addCookie(HttpServletRequest request, HttpServletResponse response, String name, String value, int second, boolean isHttpOnly) {
    	Cookie cookie = setCookie(name, value, second, isHttpOnly, null);
    	response.addCookie(cookie);
    }
}
