package com.es.web.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.web.vo.Code;
import com.es.web.vo.ResponseMap;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	/**
	 * 회원가입
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> signUp(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		memberMapper.signUp(param);
		
		return respMap.getResponseMap(Code.TEST);
	}
	
}
