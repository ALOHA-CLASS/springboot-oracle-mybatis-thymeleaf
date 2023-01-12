package com.human.controller;


import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CookieController {
	
	/**
	 * 모든 쿠키 정보 확인
	 * @param request
	 * @return
	 */
	@GetMapping("/request/cookie")
	public String cookie(HttpServletRequest request, Model model) {
		
		// 요청객체로부터 쿠키 가져오기
		Cookie[] cookies = request.getCookies();
		
		if( cookies != null ) {
			for (Cookie cookie : cookies) {
				String cookieName = cookie.getName(); 		// 쿠키 이름
				String cookieValue = cookie.getValue();		// 쿠키 값
				int maxAge = cookie.getMaxAge();			// 유효기간
				String domain = cookie.getDomain();			// 도메인
				String path = cookie.getPath();				// URL
				
				log.info("########## Cookie ##########");
				log.info("cookieName : " + cookieName);
				log.info("cookieValue : " + cookieValue);
				log.info("maxAge : " + maxAge);
				log.info("domain : " + domain);
				log.info("path : " + path);
			}
		}
		
		model.addAttribute("cookies", cookies);
		
		return "/request/cookie";
	}
	
	
	/**
	 * 쿠키 생성
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/response/cookie")
	public ResponseEntity<String> createCookie(HttpServletRequest request
											 , HttpServletResponse response){
		
		String CookieName = request.getParameter("CookieName");
		String CookieValue = request.getParameter("CookieValue");
		String path = request.getParameter("path");
		
		
		log.info("CookieName : " + CookieName);
		log.info("CookieValue : " + CookieValue);
		
		// 쿠키 생성		
		Cookie cookie = new Cookie(CookieName, CookieValue);
		cookie.setMaxAge( 60 * 60 );   			// 1시간
		// cookie.setDomain("");				// 도메인이 있어야함. (localhost 테스트 불가)
		cookie.setPath(path);
		
		// 쿠키헤더를 응답메시지에 포함시켜서 응답
		response.addCookie(cookie);
		
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		
	}
	
	
	/**
	 * 쿠키 삭제
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(path = "/response/cookie", params = "delete")
	public ResponseEntity<String> deleteCookie(HttpServletRequest request
											 , HttpServletResponse response){
		
		log.info("쿠키 삭제...");
		String CookieName = request.getParameter("CookieName");
		String path = request.getParameter("path");
		
		log.info("CookieName : " + CookieName);
		
		// 쿠키 삭제
		Cookie cookie = new Cookie(CookieName, null);	// 값을 null 로 지정	
		cookie.setMaxAge(0);   							// 유효기간 만료
		cookie.setPath(path);
		
		// 쿠키헤더를 응답메시지에 포함시켜서 응답
		response.addCookie(cookie);
		
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		
	}
	
	
	
	/**
	 * 쿠키 전체 삭제
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping(path = "/response/cookie", params = "deleteAll")
	public ResponseEntity<String> deleteAllCookie(HttpServletRequest request
											 , HttpServletResponse response){
		
		String path = request.getParameter("path");
		
		Cookie[] cookies = request.getCookies();
		
		for (Cookie cookie : cookies) {
			cookie.setValue(null);
			cookie.setMaxAge(0);
			cookie.setPath(path);
			response.addCookie(cookie);
		}
		
		
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
		
	}
	
	/**
	 * 쿠키로 저장된 아이디 가져오기
	 * @param cookie
	 * @param model
	 * @return
	 */
	// @CookieValue 
	// - value 		: 가져올 쿠키 이름을 지정
	// - required 	: 쿠키 이름이 존재하지 않을 때, 예외 발생 여부
	@GetMapping("/request/login")
	public String cookieLogin( @CookieValue(value = "remember-id", required = false) Cookie cookie
							 , Model model ) {

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
		
		return "/request/login";
	}
	
	/**
	 * 아이디 저장 시, 쿠키 생성
	 * @param request
	 * @param response
	 * @return
	 */
	@PostMapping("/request/login")
	public String createCookieLogin(HttpServletRequest request
								   ,HttpServletResponse response) {
		
		String rememberId = request.getParameter("remember-id"); 	// 아이디 저장 여부
		String username = request.getParameter("username");			// 아이디
		
		log.info("rememberId : " + rememberId);
		
		// 아이디 저장 체크 → 쿠키 생성
		if( rememberId != null && rememberId.equals("on") ) {
			Cookie cookie = new Cookie("remember-id", username);
			cookie.setMaxAge(60*60*24*10);		// 유효기간 : 10일
			cookie.setPath("/");
			response.addCookie(cookie);
		} 
		// 아이디 저장 체크X → 쿠키 삭제
		else {
			Cookie cookie = new Cookie("remember-id", null);
			cookie.setMaxAge(0);		// 유효기간 X
			cookie.setPath("/");
			response.addCookie(cookie);
		}
		
		return "redirect:/request/login";
	}
	
	
	
	
	

}
















