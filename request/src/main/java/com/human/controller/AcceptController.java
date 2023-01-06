package com.human.controller;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.domain.Board;

import lombok.extern.slf4j.Slf4j;

/**
 * Accept 매핑
 *  : Accept 헤더 값을 매핑 조건으로 매핑
 *  - produces	: 지정할 Accept 헤더 값 작성 
 *  
 *  * Accept : 클라이언트가 서버에게 수용할 수 있는 컨텐츠 타입을 알려주는 헤더정보
 *  		   "나는 이 형식으로 응답해줘~"
 * @author h
 *
 */ 
@Slf4j
@Controller
@RequestMapping("/accept")
public class AcceptController {
	
	
	@GetMapping("/request")
	public String request() {
		
		return "/accept/request";
	}
	
	
	@GetMapping(path = "/data", produces = "application/json")
	public ResponseEntity<Board> json() {
		
		Board board = new Board();
		board.setBoardNo(10);
		board.setTitle("제목");
		board.setWriter("작성자");
		board.setContent("내용");
		board.setRegDate(new Date());
		board.setUpdDate(new Date());
		
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	@GetMapping(path = "/data", produces = "application/xml")
	public ResponseEntity<Board> xml() {
		
		Board board = new Board();
		board.setBoardNo(10);
		board.setTitle("제목");
		board.setWriter("작성자");
		board.setContent("내용");
		board.setRegDate(new Date());
		board.setUpdDate(new Date());
		
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}

	
	
}















