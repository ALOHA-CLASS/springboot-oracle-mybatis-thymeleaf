package com.human.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.domain.Board;

import lombok.extern.slf4j.Slf4j;

/**
 * Content-Type 매핑
 *  : 요청 헤더 Content-Type을 매핑 조건으로 요청 경로를 매핑
 *    - consumes : 매핑 조건으로 사용할 Content-Type 헤더값을 지정
 *    ex) consumes = "application/json"
 * 
 * 
 * @author h
 *
 */
@Slf4j
@Controller
@RequestMapping("/content")
public class ContentTypeController {
	
	@GetMapping("/request")
	public String request() {
		
		return "/content/request";
	}
	
	
	@GetMapping("/requestJQuery")
	public String requestJQuery() {
		
		return "/content/requestJQuery";
	}
	
	@PostMapping(path = "/data", consumes = "text/html")
	public void html() {
		log.info("GET - /content/data - Content-Type=text/html");
		
	}
	
	// @RequestBody : 요청메시지 body(본문)의 내용을 자바 객체로 매핑하는 어노테이션
	@PostMapping(path = "/data", consumes = "application/json")
	public void json(@RequestBody Board board, Model model) {
		log.info("GET - /content/data - Content-Type=application/json");
		log.info("[board] - " + board);
		model.addAttribute("board", board);
	}
	
	@PostMapping(path = "/data", consumes = "application/xml")
	public void xml(@RequestBody Board board, Model model) {
		log.info("GET - /content/data - Content-Type=application/xml");
		log.info("[board] - " + board);
		model.addAttribute("board", board);
	}
	
	
	

}
