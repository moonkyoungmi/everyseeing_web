package com.es.web.interceptor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomInterceptor implements HandlerInterceptor {

	@Value("${login.token.name}")
	private String TOKEN_NAME;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		/**
		 *   1. 로그인을 안 했을 경우(세션에 토큰 값이 없을 경우) 로그인 페이지로 이동
		 *   2. 로그인이 되어있을 경우 프로필 선택 페이지로 이동
		 */
		
		HttpSession session = request.getSession();
		String requestUrl = request.getRequestURI();
		
		// 세션 토큰 확인
		String token = (String) session.getAttribute(TOKEN_NAME);
		
		// 토큰이 없으면 로그인 페이지로 이동
		if(requestUrl.equals("/")) {
			if(token == null || token.equals("")) {
				log.info("====================> TOKEN IS NOT EXIST");
				response.sendRedirect("/login");
				
				return false;
			}
		}
		
		// 토큰이 있으면서 로그인 페이지 또는 회원가입 페이지 접근 시 메인 페이지로 이동
		if(requestUrl.startsWith("/login") || requestUrl.startsWith("/signUp/")){
			if(token != null && !token.equals("")) {
				log.info("====================> TOKEN IS EXIST");
				response.sendRedirect("/");
				
				return false;
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}
}
