package com.es.web.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	// 회원가입
	public int signUp(Map<String, Object> param);
	
	// 회원 프로필 추가
	public int addProfile(Map<String, Object> param);
}
