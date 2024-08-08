package com.es.web.vo;

/**
 * 결과 리턴 코드
 */
public enum Code {

	OK(200, "success"),
	ERROR(500, "fail"),
	
	// MEMBER
	MEMBER_DUPLE_EMAIL(1000, "duplicate email"),
	MEMBER_EMAIL_AUTH_FAIL(1001, "email auth fail"),
	MEMBER_NOT_EXIST(1002, "member not exist"),
	MEMBER_LOGIN_FAIL(1003, "member login fail"),
	MEMBER_PROFILE_IS_FULL(1004, "member profile is fulll"),
	;
	
	public final int code;
	public final String message;
	
	Code(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
