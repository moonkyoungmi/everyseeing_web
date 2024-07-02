package com.es.web.vo;

import java.util.HashMap;
import java.util.Map;

public class ResponseMap {

	// 공통 결과 Header
	protected Map<String, Object> header;
	
	 // 공통 결과 Body
	protected Map<String, Object> body;
	
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
	
	
	/**
	 * 성공 response null
	 * @return
	 */
	public Map<String, Object> getResponseMap() {
		return getResponseMap(null);
	}
	
	/**
	 * 성공 response
	 * @param code
	 * @return
	 */
	public Map<String, Object> getResponseMap(Code code) {
		Map<String, Object> result = new HashMap<>();
		
		header.put("code", Code.OK.code);
		header.put("message", Code.OK.message);
		
		// 코드가 없을 경우 성공, 코드가 있을 경우 그에 맞는 실패 코드 리턴
		if (code == null) {
			body.put("result", true);
		} else {
			body.put("result", false);
			body.put("code", code.code);
			body.put("message", code.message);
		}
		
		result.put("header", header);
		result.put("body", body);
		
		return result;
	}
	
	/**
	 * 실패 response null
	 * @return
	 */
	public Map<String, Object> getErrorResponseMap() {
		return getErrorResponseMap(null);
	}
	
	/**
	 * 실패 response 
	 * @param code
	 * @return
	 */
	public Map<String, Object> getErrorResponseMap(Code code) {
		Map<String, Object> result = new HashMap<>();
		
		if (code == null) {
			header.put("code", Code.ERROR.code);
			header.put("message", Code.ERROR.message);
		} else {
			header.put("code", code.code);
			header.put("message", code.message);
		}
		
		result.put("header", header);
		result.put("body", body);
		
		return result;
	}
	
}
