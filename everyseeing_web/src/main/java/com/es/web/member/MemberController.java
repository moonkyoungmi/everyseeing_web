package com.es.web.member;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.web.vo.RequestMap;

@RestController
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
		Map<String, Object> param = reqMap.getMap();
		
		return memberService.signUp(param);
	}
	
}
