package com.es.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/")
	public String main() {
		
		return "main";
	}
	
	@GetMapping("/login")
	public String login() {
		
		return "page/login";
	}

	@GetMapping("/signUp")
	public String signUp() {
		
		return "page/signUp";
	}
	
	@GetMapping("/profile")
	public String profile() {
		
		return "page/profile";
	}
	
}
