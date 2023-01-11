package com.human.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	// 사용자 페이지 - /user
	@GetMapping("/user")
	public String user() {
		return "/user/index";
	}
	
	
	// 관리자 페이지 - /admin
	@GetMapping("/admin")
	public String admin() {
		return "/admin/index";
	}
	
	// 로그인 페이지 - /auth/login
	@GetMapping("/auth/login")
	public String login() {
		return "/auth/login";
	}
	
	

}





