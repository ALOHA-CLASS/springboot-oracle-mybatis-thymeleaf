package com.human.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.human.domain.Board;
import com.human.domain.Member;

import lombok.extern.slf4j.Slf4j;

/**
 * 컬렉션 타입 응답
 * - 반환 타입을 컬렉션 타입으로, JSON 객체 배열 형식으로 변환되어 응답한다.
 * @author h
 *
 */
@Slf4j
@Controller
@RequestMapping("/collection")
public class CollectionController {

	@ResponseBody
	@GetMapping("/list/board")
	public List<Board> boardList() {
		List<Board> boardList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Board board = new Board(i+1, "제목","작성자","내용", new Date(), new Date() );
			boardList.add(board);
		}
		return boardList;
	}
	
	@ResponseBody
	@GetMapping("/list/member")
	public List<Member> memberList() {
		List<Member> memberList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Member member = new Member(i+1,"human","123456","김휴먼","khm@human.com",new Date(), new Date());
			memberList.add(member);
		}
		return memberList;
	}
	
	@ResponseBody
	@GetMapping("/map/board")
	public Map<String, Board> boardMap(  ) {
		log.info("boardMap 요청...");
		
		Map<String, Board> boardMap = new HashMap();
		for (int i = 0; i < 5; i++) {
			boardMap.put("게시글" + (i+1), new Board((i+1), "제목", "작성자", "내용", new Date(), new Date()));
		}
		return boardMap;
	}
	
	
	@ResponseBody
	@GetMapping("/map/member")
	public Map<String, Member> memberMap(  ) {
		log.info("memberMap 요청...");
		
		Map<String, Member> memberMap = new HashMap();
		for (int i = 0; i < 5; i++) {
			memberMap.put("회원" + (i+1), new Member(i+1,"human","123456","김휴먼","khm@human.com",new Date(), new Date() ));
		}
		return memberMap;
	}
	
	
	
	
	
}














