package com.es.web.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.es.web.email.MailData;
import com.es.web.email.MailService;
import com.es.web.email.template.JoinAuthTemplate;
import com.es.web.util.CommonUtil;
import com.es.web.util.SHAUtil;
import com.es.web.vo.ResponseMap;

@Service
@Transactional
public class MemberService {

	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private MailService mailService;
	
	/**
	 * 회원가입
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> signUp(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		// 비밀번호 암호화
		String password = (String) param.get("password");
		String encPassword = SHAUtil.encrypt(password);
		param.put("enc_password", encPassword);
		
		// 회원가입
		if(memberMapper.signUp(param) <= 0) {
			return respMap.getErrorResponseMap();
		}
		param.put("idx_member", param.get("idx"));
		
		// 최초 프로필 1개 등록
		param.put("nickname", "기본");
		
		if(memberMapper.addProfile(param) <= 0) {
			return respMap.getErrorResponseMap();
		}
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 인증번호 이메일 발송
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> sendMail(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		String authNum = CommonUtil.makeRandStr(10);
		param.put("auth_num", authNum);
		
		// 인증번호 DB 저장
		memberMapper.saveAuthNum(param);
		
		// 메일 템플릿 설정
		String email = (String) param.get("email");
	 	JoinAuthTemplate template = new JoinAuthTemplate();
	 	template.setAuthNum(authNum);
 		
 		// 메일 발송
 		MailData mailData = new MailData(email, template);
 		mailService.sendMail(mailData);
		
		return respMap.getResponseMap();
	}
	
	/**
	 * 이메일 인증번호 확인
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> checkAuthNum(Map<String, Object> param) throws Exception {
		ResponseMap respMap = new ResponseMap();
		
		Map<String, Object> data = memberMapper.getAuthNum(param);
		String savedAuthNum = (String) data.get("auth_number");
		String authNum = (String) param.get("auth_num");
		
		if(!savedAuthNum.equals(authNum)) {
			return respMap.getErrorResponseMap();
		}
		
		respMap.setBody("data", data);
		
		return respMap.getResponseMap();
	}
}
