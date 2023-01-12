package com.human.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
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
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	// 메인 페이지  - /
	@GetMapping("/")
	public String main() {
		return "/index";
	}
	
	
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
	public String login( Model model
					  , @CookieValue(value="remember-id", required = false) Cookie cookie ) {
		
		String userId = "";
		boolean rememberId = false;
		
		// remember-id 쿠키가 존재하면,
		if( cookie != null ) {
			log.info("CookieName : " + cookie.getName() );
			log.info("CookieValue : " + cookie.getValue() );
			userId = cookie.getValue();		// 쿠키에 저장된 아이디
			rememberId = true;				// 아이디 저장 여부
		}
		
		model.addAttribute("userId", userId);
		model.addAttribute("rememberId", rememberId);
		
		return "/auth/login";
	}
	
	// 회원 가입 	- /auth/join
	@GetMapping("/auth/join")
	public String joinForm() {
	
		return "/auth/join";
	}
	
	
	// 회원 가입 처리 	- /auth/join
	@PostMapping("/auth/join")
	public String join(Users user, RedirectAttributes rttr, HttpServletRequest request) throws Exception {
		log.info("user : " + user);
		
		// 회원가입 서비스 요청
		int result = userService.join(user);
		
		// 회원가입 성공 시, 바로 로그인
		if( result > 0 ) {
			HttpSession session = joinAndAuthenticaion(user, request);
		}
		
		// RedirectAttributes : 리다이렉트 될 경로에 전송할 데이터를 가지고 있는 인터페이스
		// addFlashAttribute("파라미터명", 값)
		// : 리다이렉트 하면서 임시로 저장할 데이터를 등록하는 메소드
		rttr.addFlashAttribute("msg", user.getName() );
		
		// return "redirect:/auth/success"; // 가입완료 페이지
		return "redirect:/";
	}
	
	// 회원 가입 완료 - /auth/success
	@GetMapping("/auth/success")
	public String success() {
	
		return "/auth/success";
	}
	
	
	// 회원가입 후 바로 로그인 처리
	private HttpSession joinAndAuthenticaion(Users user, HttpServletRequest request) throws Exception {
		
		String username = user.getUserId();
		String password = user.getUserPwChk();			// userPw 는 암호화 되기 때문에, userPwChk 를 사용
		log.info("username : " + username);
		log.info("password : " + password);
		
		HttpSession session = request.getSession();
		
		// 아이디, 패스워드로 인증토큰 생성
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( username, password );
		
		// 토큰에 요청정보를 등록
		token.setDetails( new WebAuthenticationDetails(request) );
		
		// 토큰을 이용하여 인증 (로그인)
		Authentication authentication = authenticationManager.authenticate(token);
		
		User authUser = (User) authentication.getPrincipal();
		log.info("인증된 사용자 아이디: " + authUser.getUsername());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return session;
	}
	
	

}

























