package com.es.web.vo;

/**
 * 결과 리턴 코드
 */
public enum Code {

	OK(200, "success"),
	ERROR(500, "fail"),
	
	TEST(1000, "test"),
	;
	
	public final int code;
	public final String message;
	
	Code(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
