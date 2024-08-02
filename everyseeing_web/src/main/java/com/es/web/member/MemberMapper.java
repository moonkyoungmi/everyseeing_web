package com.es.web.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	// 회원가입
	public int signUp(Map<String, Object> param);
	
	// 회원 프로필 추가
	public int addProfile(Map<String, Object> param);
	
	// 이메일 인증번호 저장
	public int saveAuthNum(Map<String, Object> param);

	// 인증번호 확인
	public Map<String, Object> getAuthNum(Map<String, Object> param);
	
	// 인증번호 확인 업데이트
	public int modifyAuthNumCheck(Map<String, Object> param);
	
	// 이메일 인증 유무 확인
	public Map<String, Object> emailAuthCheck(Map<String, Object> param);
	
	// 이메일 중복 확인
	public int duplicateEmailCheck(Map<String, Object> param);
	
}
