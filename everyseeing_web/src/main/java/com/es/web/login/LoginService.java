package com.es.web.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.web.util.CommonUtil;
import com.es.web.util.JWTUtil;
import com.es.web.util.SHAUtil;
import com.es.web.vo.Code;
import com.es.web.vo.ResponseMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class LoginService {

	@Autowired
	private LoginMapper loginMapper;
	
	@Autowired
	private JWTUtil jwtUtil;
	
	@Value("${login.token.name}")
	private String TOKEN_NAME;
	
	/**
	 * 로그인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> login(Map<String, Object> param, HttpServletRequest request) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		Map<String, Object> memberInfo = loginMapper.getMemberInfo(param);
		if(CommonUtil.checkIsNull(memberInfo)) {
			// 회원 미존재
			return respMap.getResponseMap(Code.MEMBER_NOT_EXIST);
		}
		
		String idxMember = String.valueOf(memberInfo.get("idx_member"));
		param.put("idx_member", idxMember);

		String savesPassword = (String) memberInfo.get("password");
		String encPassword = SHAUtil.encrypt(String.valueOf(param.get("password")));
		if(!savesPassword.equals(encPassword)) {
			// 비밀번호 불일치
			param.put("login_yn", "N");
			addLoginHistory(param);
			return respMap.getResponseMap(Code.MEMBER_LOGIN_FAIL);
		} else {
			// 비밀번호 일치
			param.put("login_yn", "Y");
			addLoginHistory(param);
			
			// JWT 토큰 발급
			Map<String, Object> claims = new HashMap<>();
			claims.put("login_ip", param.get("ip"));
			claims.put("login_idx", memberInfo.get("idx_member"));
			
			String accessToken = jwtUtil.createToken(claims);
			log.debug("====================> ACCESS TOKEN IS " + accessToken);
			
			// 세션 저장
			HttpSession session = request.getSession();
			session.setAttribute(TOKEN_NAME, accessToken);
		}

		return respMap.getResponseMap();
	}
	
	/**
	 * 로그인 기록 추가
	 * @param param
	 * @throws Exception
	 */
	private void addLoginHistory(Map<String, Object> param) throws Exception {
		loginMapper.addLoginHistory(param);
	}
	
	/**
	 * 로그아웃
	 * @param param
	 * @return
	 * @throws Exception
	 */
	@Deprecated
	public Map<String, Object> logout(Map<String, Object> param, HttpServletRequest request) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		HttpSession session = request.getSession();
		session.removeAttribute(TOKEN_NAME);
		
		return respMap.getResponseMap();
	}
}
