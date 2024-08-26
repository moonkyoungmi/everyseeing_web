package com.es.web.filter;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.es.web.util.CommonUtil;
import com.es.web.util.CookieUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * AES256 암호화를 위한 SecretKey 생성 필터
 * 세션과 쿠키에 저장
 */

@Component
public class SecretKeyFilter extends OncePerRequestFilter {
	
	@Value("${session.secret.key}")
	private String SECRET_KEY;

	@Value("${session.default.key}")
	private String DEFAULT_SECRET_KEY;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		try {
			HttpSession session = request.getSession();
			String secretKey = (String) session.getAttribute(SECRET_KEY);
			
			// 세션 및 쿠키에 저장된 키가 없거나 두 값이 다를 경우 새 키 생성
			Optional<Cookie> cookies = CookieUtil.getCookie(request, SECRET_KEY);
			if(cookies.isPresent()) {
				if(secretKey == null || secretKey == "" || secretKey.equals(DEFAULT_SECRET_KEY) || !cookies.get().getValue().equals(secretKey)) {
					secretKey = CommonUtil.makeSecKey();
					session.setAttribute(SECRET_KEY, secretKey);
				}
			} else {
				secretKey = DEFAULT_SECRET_KEY;
			}
			
			// 클라이언트에 쿠키 전달
			CookieUtil.addCookie(request, response, SECRET_KEY, secretKey, 1800, false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		filterChain.doFilter(request, response);
	}

}
