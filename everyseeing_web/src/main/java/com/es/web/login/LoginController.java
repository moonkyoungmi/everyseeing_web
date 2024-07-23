package com.es.web.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.web.vo.RequestMap;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	/**
	 * 로그인
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login")
	public Map<String, Object> login(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return loginService.login(param);
	}
	
	/**
	 * 로그아웃
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	public Map<String, Object> logout(RequestMap reqMap) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return loginService.logout(param);
	}
}
