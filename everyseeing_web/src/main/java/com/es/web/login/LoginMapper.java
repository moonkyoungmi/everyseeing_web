package com.es.web.login;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginMapper {

	// 회원 정보 가져오기
	public Map<String, Object> getMemberInfo(Map<String, Object> param);
}
