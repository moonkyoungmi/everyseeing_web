package com.es.web.vo;

import org.springframework.stereotype.Component;

@Component
public class BeanFactory {

	public ResponseMap getResponseMap() {
		return new ResponseMap();
	}
}
