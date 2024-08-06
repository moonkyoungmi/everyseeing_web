package com.es.web.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.es.web.util.AESUtil;
import com.es.web.util.CommonUtil;
import com.es.web.util.JWTUtil;
import com.es.web.vo.RequestMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

	@Autowired
	private JWTUtil jwtUtil;
	
	@Value("${login.token.name}")
	private String TOKEN_NAME;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().isAssignableFrom(RequestMap.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		Map<String, Object> headerMap = new HashMap<String, Object>();
		Map<String, Object> bodyMap = new HashMap<String, Object>();

		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

		// 헤더 설정
		headerMap.put("ip", request.getRemoteAddr());
		
		// 토큰 파싱 후 idx 추가
		HttpSession session = request.getSession();
		String token = (String) session.getAttribute(TOKEN_NAME);
		
		// 토큰이 있을 경우 idx_member 파라미터에 추가
		if(token!= null && !token.equals("")) {
			// 토큰 파싱
			Map<String, Object> claims = jwtUtil.parseJwtToken(token);
			bodyMap.put("idx_member", claims.get("login_idx"));
		}
		
		// 파라미터 설정
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement();
			String[] values = request.getParameterValues(key);

			if(values != null && key.equals("d")) {
				// AES256 복호화
				String jsonStr = AESUtil.decrypt(values[0], CommonUtil.getSecKey(request));
				
				Map<String, Object> dataMap = CommonUtil.convertJsonToMap(jsonStr);
				// 특수문자 이스케이프
				for(String k : dataMap.keySet()) {
					String escapeValue = "";
					if(dataMap.get(k) != null && dataMap.get(k) != "") {
						String value = String.valueOf(dataMap.get(k));
						escapeValue = StringEscapeUtils.escapeHtml4(value);

						bodyMap.put(k, escapeValue);
					} else {
						bodyMap.put(k, dataMap.get(k));
					}
				}
			} else {
				bodyMap.put(key, values[0]);
			}
		}

		return new RequestMap(headerMap, bodyMap);
	}

}