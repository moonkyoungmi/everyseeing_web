package com.es.web.util;

import java.util.HashMap;
import java.util.Map;

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
}
