package com.es.web.login;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.es.web.vo.RequestMap;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class LoginController {

	@Autowired
	private LoginService loginService;
	
	@Value("${login.token.name}")
	private String TOKEN_NAME;
	
	/**
	 * 로그인
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@PostMapping("/login")
	public Map<String, Object> login(RequestMap reqMap, HttpServletRequest request) throws Exception {
		Map<String, Object> param = reqMap.getMap();
		
		return loginService.login(param, request);
	}
	
	/**
	 * 로그아웃
	 * @param reqMap
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/logout")
	public void logout(RequestMap reqMap, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		HttpSession session = request.getSession();
		session.removeAttribute(TOKEN_NAME);
		
		response.sendRedirect("/login");
	}
}
