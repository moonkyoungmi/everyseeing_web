package com.es.web.member;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

	// 회원가입
	public Map<String, Object> signUp(Map<String, Object> param);
}
