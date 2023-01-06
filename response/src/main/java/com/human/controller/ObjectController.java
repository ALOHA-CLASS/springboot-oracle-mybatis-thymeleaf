package com.human.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.human.domain.Board;
import com.human.domain.Member;

import lombok.extern.slf4j.Slf4j;

/**
 * 자바 클래스 타입 응답
 * - 객체 타입으로 응답을 하면, 자동으로 JSON 타입으로 변환하여 응답된다.
 * - @ResponseBody 어노테이션을 메소드에 선언해야한다.
 * - @ResponseBody : 지정한 반환타입의 데이터를 응답 메시지의 body(본문) 전달되도록 하는 어노테이션 
 * @author h
 *
 */
@Slf4j
@Controller
@RequestMapping("/object")
public class ObjectController {
	
	@ResponseBody					// 응답 객체를 응답 메시지 body에 매핑하여 전송
	@GetMapping("/board")
	public Board board() {
		log.info("Board 객체 응답...");
		
		// Board 객체 생성
		Board board = new Board(10, "제목","작성자","내용", new Date(), new Date() );
		
		return board;
	}
	
	@ResponseBody					// 응답 객체를 응답 메시지 body에 매핑하여 전송
	@GetMapping("/member")
	public Member member() {
		log.info("Member 객체 응답...");
		
		// Member 객체 생성
		Member member = new Member(1,"human","123456","김휴먼","khm@human.com",new Date(), new Date());
		
		return member;
	}
	
	
	

}











