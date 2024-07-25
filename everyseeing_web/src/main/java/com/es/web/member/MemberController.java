package com.es.web.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.web.vo.RequestMap;

@RestController
@RequestMapping("/member")
public class MemberController {

	@Autowired
	private MemberService memberService;
	
	/**
	 * 회원가입
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/signUp")
	public Map<String, Object> signUp(RequestMap reqMap) throws Exception {
		
		System.out.println(reqMap);
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.signUp(param);
	}
	
	/**
	 * 인증번호 이메일 발송
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/send/mail")
	public Map<String, Object> sendMail(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.sendMail(param);
	}
	
}
