package com.es.web.vo;

import java.util.HashMap;
import java.util.Map;

public class RequestMap {
	private Map<String, Object> header;
	private Map<String, Object> body;
	
	public RequestMap(Map<String, Object> header, Map<String, Object> body) {
		this.header = header;
		this.body = body;
	}
	
	/*
	 * 공통 리퀘스트 객체
	 */
	public Map<String, Object> getMap() {
		Map<String, Object> result = new HashMap<>();
		System.out.println("body: " + body);
		result.putAll(header);
		result.putAll(body);
		
		return result;
	}
	
}
