package com.human.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


/**
 * 요청 경로 매핑
 * - @RequestMapping 
 *   : 클라이언트의 요청과 연결할 메소드에 작성하여 요청 경로을 설정하는 어노테이션
 *   - value 	: 요청경로를 지정하는 속성
 *   - path		: 요청경로를 지정하는 속성
 *   * 요청경로만 지정할 때는 속성명을 생략할 수 있다.
 * 
 * @author h
 *
 */
@Slf4j			// lombok 라이브러리를 이용해서, 로그출력을 사용할 수 있다.
@Controller
public class RequestController {
	
	@RequestMapping(value = "/test")
	public void test() {
		
	}
	
	@RequestMapping(path = "/test2")
	public void test2() {
		
	}
	
	@RequestMapping("/test3")
	public void test3() {
		
	}
	

}
