package com.es.web.test;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.es.web.vo.BeanFactory;
import com.es.web.vo.Code;
import com.es.web.vo.ResponseMap;

@Service
public class TestService {

	@Autowired
	BeanFactory beanFactory;
	
	public Map<String, Object> getList(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		Map<String, Object> result = new HashMap<>();
		result.put("1", 1);
		
		respMap.setBody("result", result);
		
		return respMap.getResponseMap(Code.TEST);
	}
}
