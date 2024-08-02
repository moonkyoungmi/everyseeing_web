package com.es.web.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.web.vo.Code;
import com.es.web.vo.ResponseMap;

@Service
@Transactional
public class LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> login(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		/**
		 * 1. 이메일, 비밀번호 입력
		 * 2. 이메일로 회원 유무 확인
		 * 3. 회원이 있을 경우 입력한 비밀번호를 암호화 하여 DB 비밀번호와 비교
		 * 4. 비밀번호가 맞을 경우 로그인 기록 Y 추가, 틀릴 경우 N 추가
		 * 5. 로그인 성공 후 JWT 토큰 발급
		 */
		
		Map<String, Object> memberInfo = loginMapper.getMemberInfo(param);
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 로그아웃
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> logout(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		return respMap.getResponseMap();
	}
}
