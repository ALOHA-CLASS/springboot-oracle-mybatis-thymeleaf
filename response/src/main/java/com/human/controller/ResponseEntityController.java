package com.human.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.human.domain.Board;
import com.human.domain.Member;

import lombok.extern.slf4j.Slf4j;

/**
 * ResponseEntity<> 응답 객체
 * - 응답 메시지의 헤더정보와 내용을 설정하는 객체
 * - 생성 : new ResponseEntity<응답할 데이터 타입>(객체, 상태코드);
 *      * HttpStatus.상태코드상수
 *      
 * - ex) new ResponseEntify<String>("OK", HttpStatus.OK);
 * -     new ResponseEntify<Board>( new Board(), HttpStatus.OK); 
 * @author h
 *
 */
@Slf4j
@Controller
@RequestMapping("/response")
public class ResponseEntityController {
	
	@ResponseBody
	@GetMapping("/void")
	public ResponseEntity<Void> responseVoid() {
		log.info("상태코드만 응답 - 200 OK");
		return new ResponseEntity<Void>(HttpStatus.NOT_FOUND);
	}
	
	@ResponseBody
	@GetMapping("/string")
	public ResponseEntity<String> responseString() {
		log.info("COMPLETE - 200 OK");
		return new ResponseEntity<String>("COMPLETE", HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping("/board")
	public ResponseEntity<Board> responseBoard() {
		log.info("Board - 200 OK");
		Board board = new Board(1, "제목", "작성자", "내용", new Date(), new Date());
		return new ResponseEntity<Board>(board, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/member")
	public ResponseEntity<Member> responseMember() {
		log.info("Member - 200 OK");
		Member member = new Member(1, "KHM", "123456", "김휴먼", "khm@human.com", new Date(), new Date());
		return new ResponseEntity<Member>(member, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping("/list/board")
	public ResponseEntity<List<Board>> boardList() {
		List<Board> boardList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Board board = new Board(i+1, "제목","작성자","내용", new Date(), new Date() );
			boardList.add(board);
		}
		return new ResponseEntity<List<Board>>(boardList, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/list/member")
	public ResponseEntity<List<Member>> memberList() {
		List<Member> memberList = new ArrayList<>();
		for (int i = 0; i < 5; i++) {
			Member member = new Member(i+1,"human","123456","김휴먼","khm@human.com",new Date(), new Date());
			memberList.add(member);
		}
		return new ResponseEntity<List<Member>>(memberList, HttpStatus.OK);
	}
	
	@ResponseBody
	@GetMapping("/map/board")
	public ResponseEntity<Map<String, Board>> boardMap(  ) {
		log.info("boardMap 요청...");
		
		Map<String, Board> boardMap = new HashMap();
		for (int i = 0; i < 5; i++) {
			boardMap.put("게시글" + (i+1), new Board((i+1), "제목", "작성자", "내용", new Date(), new Date()));
		}
		return new ResponseEntity<Map<String, Board>>(boardMap, HttpStatus.OK);
	}
	
	
	@ResponseBody
	@GetMapping("/map/member")
	public ResponseEntity<Map<String, Member>> memberMap(  ) {
		log.info("memberMap 요청...");
		
		Map<String, Member> memberMap = new HashMap();
		for (int i = 0; i < 5; i++) {
			memberMap.put("회원" + (i+1), new Member(i+1,"human","123456","김휴먼","khm@human.com",new Date(), new Date() ));
		}
		return new ResponseEntity<Map<String, Member>>(memberMap, HttpStatus.OK);
	}
	
	// 이미지 파일 데이터를 응답
	@ResponseBody
	@GetMapping("/img")
	public ResponseEntity<byte[]> responseImg() throws Exception {
		
		ResponseEntity<byte[]> response = null;

		try {
			File file = new File("C:\\KHM\\upload\\test.png");
			// File --> byte[]
			// java.nio.file.Files 이용해서 File to byte[] 변환
			// byte[] imgData = Files.readAllBytes( file.toPath() );
			
			// springframework 이용해서 File to byte[] 변환
			// - FileCopyUtils : 파일 관련 유용한 기능을 제공하는 클래스(파일복사 등)
			byte[] imgData = FileCopyUtils.copyToByteArray(file);
			
			// 헤더 정보 객체
			HttpHeaders header = new HttpHeaders();
			header.setContentType( MediaType.IMAGE_PNG );
			
			// new ResponseEntity<객체타입>(객체, 상태코드);
			// new ResponseEntity<객체타입>(객체, 헤더객체, 상태코드);
			response = new ResponseEntity<byte[]>(imgData, header, HttpStatus.OK);
			
		} catch(IOException e) {
			e.printStackTrace();
			// 400 - 클라이언트의 잘못된 요청
			response = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
		
	}
	
	
	// 이미지 파일 다운로드
	@ResponseBody
	@GetMapping("/download/img")
	public ResponseEntity<byte[]> downloadImg() throws Exception {
		
		ResponseEntity<byte[]> response = null;

		try {
			File file = new File("C:\\KHM\\upload\\test.png");
			// File --> byte[]
			// java.nio.file.Files 이용해서 File to byte[] 변환
			// byte[] imgData = Files.readAllBytes( file.toPath() );
			
			// springframework 이용해서 File to byte[] 변환
			// - FileCopyUtils : 파일 관련 유용한 기능을 제공하는 클래스(파일복사 등)
			byte[] imgData = FileCopyUtils.copyToByteArray(file);
			
			// 헤더 정보 객체
			HttpHeaders header = new HttpHeaders();
			header.setContentType( MediaType.APPLICATION_OCTET_STREAM );  // 일반 파일
			String fileName = "test.png";
			fileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");		// 인코딩된 파일명
			
			// Content-Disposition : attachment; filename="test.png"
			header.add("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
			
			// new ResponseEntity<객체타입>(객체, 상태코드);
			// new ResponseEntity<객체타입>(객체, 헤더객체, 상태코드);
			//  HttpStatus.CREATED : 201 상태코드 : 요청 성공 및 자원 생성
			response = new ResponseEntity<byte[]>(imgData, header, HttpStatus.CREATED);
			
		} catch(IOException e) {
			e.printStackTrace();
			// 400 - 클라이언트의 잘못된 요청
			response = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
		}
		
		return response;
		
	}
	
}
























