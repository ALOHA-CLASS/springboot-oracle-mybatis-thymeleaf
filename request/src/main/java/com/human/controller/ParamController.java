package com.human.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;


/**
 * @XXXMapping(path = "요청경로", params="요청파라미터")
 * - path 		: 요청경로 지정
 * - params 	: 요청 파라미터를 매핑 조건으로 지정
 *  * 같은 경로에 대해서 요청 파라미터에 따라 호출할 메소드를 지정할 수 있다. 
 * @return
 */
@Slf4j
@Controller
@RequestMapping("/param")
public class ParamController {
	
	@GetMapping(path = "/get")
	public String param() {
		
		return "/param/get";
	}
	
	@GetMapping(path = "/get", params = "param1")
	public String param1(String param1) {
		log.info("param1 : " + param1);
		
		return "/param/get";
	}
	
	@GetMapping(path = "/get", params = "param2")
	public String param2(String param2) {
		log.info("param2 : " + param2);
		
		return "/param/get";
	}
	
	// form 요청은 GET/POST 만 지원 가능
	// : post방식으로 같은 경로에 대해
	//   요청 파라미터를 조건으로 매핑
	@PostMapping(path = "/post", params = "insert")
	public String insert() {
		log.info("게시글 쓰기 요청...");
		
		return "/param/result";
	}
	
	@PostMapping(path = "/post", params = "update")
	public String update() {
		log.info("게시글 수정 요청...");
		
		return "/param/result";
	}
	
	@PostMapping(path = "/post", params = "delete")
	public String delete() {
		log.info("게시글 삭제 요청...");
		
		return "/param/result";
	}

}











