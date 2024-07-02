package com.es.web.test;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.web.vo.RequestMap;

@RestController
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private TestService testService;

	@PostMapping("/getList")
	public Map<String, Object> getList(RequestMap reqMap) throws Exception {

		Map<String, Object> param = reqMap.getMap();
		
		return testService.getList(param);
	}
	
}
