package com.human.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

/**
 * void 타입 응답
 * : 요청 URL 과 동일한 뷰 파일(html)을 응답한다. 
 * - RESTful 개발에서는 내용이 없는 메시지를 응답한다.
 * @author h
 *
 */
@Slf4j
@Controller
@RequestMapping("/empty")
public class VoidController {
	// /empty/sup
	// /empty/sup/sub
	
	@GetMapping("/sup")
	public void test() {
		log.info("/empty/sup.html 이 응답된다.");
	}
	
	@GetMapping("/sup/sub")
	public void test2() {
		log.info("/empty/sup/sub.html 이 응답된다.");
	}
			

}








