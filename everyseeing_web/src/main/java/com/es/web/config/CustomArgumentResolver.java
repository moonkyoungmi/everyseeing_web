package com.es.web.config;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.text.StringEscapeUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.es.web.util.AESUtil;
import com.es.web.util.CommonUtil;
import com.es.web.vo.RequestMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class CustomArgumentResolver implements HandlerMethodArgumentResolver {

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
		
		// 파라미터 설정
		Enumeration<String> parameterNames = request.getParameterNames();
		while (parameterNames.hasMoreElements()) {
			String key = parameterNames.nextElement();
			String[] values = request.getParameterValues(key);

			if(values != null && key.equals("d")) {
				// AES256 복호화
				String jsonStr = AESUtil.decrypt(values[0], CommonUtil.getSecKey(request));
				
				System.out.println("===========!!!! " + jsonStr);
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