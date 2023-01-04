package com.human.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * 헤더 매핑
 * : 헤더를 매핑 조건으로하여 요청 경로 지정
 * 
 * @XXXMapping(path = "요청경로", headers = "헤더 속성")
 * - headers : 헤더를 매핑 조건으로 지정
 * 
 * @author h
 *
 */
@Slf4j
@Controller
@RequestMapping("/header")
public class HeaderController {
	
	@GetMapping("/test")
	public String test() {
		log.info("/header/test");
		return "/header/test";
	}
	
	@GetMapping(path = "/test", headers = "Content-Disposition")
	public String testHeader() {
		log.info("/header/test - Content-Disposition");
		return "/header/test";
	}
	

}




















