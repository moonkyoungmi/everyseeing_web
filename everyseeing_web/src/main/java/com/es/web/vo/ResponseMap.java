package com.es.web.vo;

import java.util.HashMap;
import java.util.Map;

public class ResponseMap {

	// 공통 결과 Header
	protected Map<String, Object> header;
	
	 // 공통 결과 Body
	protected Map<String, Object> body;
	
	// 생성자
	public ResponseMap() {
		this.header = new HashMap<String, Object>();
		this.body = new HashMap<String, Object>();
	}

	public Map<String, Object> getBody() {
		return body;
	}

	public void setBody(Map<String, Object> data) {
		this.body.putAll(data);
	}

	public void setBody(String key, Object data) {
		this.body.put(key, data);
	}
	
	public Map<String, Object> getResponseMap() {
		Map<String, Object> result = new HashMap<>();
		
		header.put("code", "OK");
		
		result.put("header", header);
		result.put("body", body);
		
		return result;
	}
	
	
}
