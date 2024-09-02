package com.es.web.page;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

	@GetMapping("/")
	public String main() {
		
		return "page/home";
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
	
	@GetMapping("/setting")
	public String setting() {
		
		return "page/setting";
	}
	
}
