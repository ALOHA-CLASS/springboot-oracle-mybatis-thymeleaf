package com.human.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.human.domain.Users;
import com.human.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
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
	
	// 회원 가입 	- /auth/join
	@GetMapping("/auth/join")
	public String joinForm() {
	
		return "/auth/join";
	}
	
	
	// 회원 가입 처리 	- /auth/join
	@PostMapping("/auth/join")
	public String join(Users user, RedirectAttributes rttr) throws Exception {
		log.info("user : " + user);
		
		// 회원가입 서비스 요청
		int result = userService.join(user);
		
		// RedirectAttributes : 리다이렉트 될 경로에 전송할 데이터를 가지고 있는 인터페이스
		// addFlashAttribute("파라미터명", 값)
		// : 리다이렉트 하면서 임시로 저장할 데이터를 등록하는 메소드
		rttr.addFlashAttribute("msg", user.getName() );
		
		return "redirect:/auth/success";
	}
	
	// 회원 가입 완료 - /auth/success
	@GetMapping("/auth/success")
	public String success() {
	
		return "/auth/success";
	}
	
	

}
















