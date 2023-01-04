package com.human.board.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.human.board.dao.BoardDAO;
import com.human.board.domain.Board;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class BoardController {
	
	// 게시판 프로젝트
	// Controller
	// - 요청경로 매핑
	// 게시글 목록 - 화면		GET 	/board/list
	
	// 게시글 쓰기 - 화면		GET 	/board/insert
	// 게시글 쓰기 - 처리		POST	/board/insert
	
	// 게시글 읽기 - 화면		GET		/board/read
	
	// 게시글 수정 - 화면		GET		/board/update
	// 게시글 수정 - 처리		PUT		/board/update
	
	// 게시글 삭제 - 처리		DELETE	/board/delete

	BoardDAO boardDAO = new BoardDAO();
	
	/**
	 * 게시글 목록 - 화면		GET 	/board/list
	 * - 전체 게시글 목록 조회
	 * - 게시글 목록을 모델에 등록
	 * - /board/list.html 뷰를 응답
	 * @return
	 */
	@GetMapping("/board/list")
	public String list(Model model) {
		List<Board> boardList = boardDAO.selectList();
		
		model.addAttribute("boardList", boardList);
		
		return "/board/list";		// board/list.html
	}
	
	/**
	 * 게시글 쓰기 - 화면		GET 	/board/insert
	 * - /board/insert.html 뷰를 응답
	 * @return
	 */
	@GetMapping("/board/insert")
	public String insertForm() {
		
		return "/board/insert";		// board/insert.html
	}
	
	/**
	 * 게시글 쓰기 - 처리		POST	/board/insert
	 * - (제목/작성자/내용)을 요청파라미터로 전달 받음
	 * - 게시글을 등록 처리
	 * - 게시글 목록으로 리다이렉트 → /board/list.html 뷰를 응답
	 * @param title
	 * @param writer
	 * @param content
	 * @return
	 */
	@PostMapping("/board/insert")
	public String insert(@RequestParam("title") String title
					    ,@RequestParam("writer") String writer
					    ,@RequestParam("content") String content) {
		// @RequestParam("파라미터명") 타입 매개변수명
		// : 요청 파라미터를 매개변수로 가져온다
		//   * 요청 파라미터명과 매개변수명이 일치하면 생략가능
		Board board = new Board();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.insert(board);
		
		
		
		System.out.println("title : " + title);
		System.out.println("writer : " + writer);
		System.out.println("content : " + content);
		
		return "redirect:/board/list";		// 게시글 쓰기 처리 후, 게시글 목록으로 리다이렉트
	}
	
	@GetMapping("/board/read")
	public String readWarning() {
		
		return "/board/warning";
	}
	
	/**
	 * 게시글 읽기 - 화면		GET		/board/read
	 * - 게시글 번호를 요청파라미터로 전달 받음
	 * - 게시글 번호로 게시글 정보 조회
	 * - 게시글 정보를 모델에 등록
	 * - /board/read.html 뷰를 응답
	 * @param model
	 * @param boardNo
	 * @return
	 */
	@GetMapping(path = "/board/read", params = "boardNo")
	public String read(Model model, int boardNo) {
		// 게시글 번호로 게시글 정보를 조회
		System.out.println("게시글 조회...");
		System.out.println("boardNo : " + boardNo);
		
		Board board = boardDAO.select(boardNo);
		
		model.addAttribute("boardNo", boardNo);
		model.addAttribute("board", board);
		return "/board/read";
	}
	
	
	@GetMapping("/board/update")
	public String updateWarning() {
		
		return "/board/warning";
	}
	
	/**
	 * 게시글 수정 - 화면		GET		/board/update
	 * - 게시글 번호를 요청파라미터로 전달 받음
	 * - 게시글 번호로 게시글 정보 조회
	 * - 게시글 정보를 모델에 등록
	 * - /board/update.html 뷰를 응답
	 * @param model
	 * @param boardNo
	 * @return
	 */
	@GetMapping(path = "/board/update", params = "boardNo")
	public String updateForm(Model model, int boardNo) {
		// 게시글 번호로 게시글 정보를 조회
		System.out.println("게시글 조회(수정화면)...");
		System.out.println("boardNo : " + boardNo);
		
		Board board = boardDAO.select(boardNo);
		
		model.addAttribute("boardNo", boardNo);
		model.addAttribute("board", board);
		return "/board/update";
	}
	
	/**
	 * 게시글 수정 - 처리		PUT		/board/update
	 * - 수정할 (게시글번호/제목/작성자/내용) 요청파라미터로 전달 받음
	 * - 게시글 번호를 조건으로 게시글 정보 수정 처리
	 * - 게시글 읽기로 리다이렉트 → /board/read.html 응답
	 * @param request
	 * @return
	 */
	@PutMapping("/board/update")
	public String update(HttpServletRequest request) {
		// HttpServletRequest
		// - 클라이언트의 요청과 관련한 기능을 사용할 수 있는 요청객체
		// - getParameter("파라미터명")   : 요청 파라미터의 값을 가져온다	
		//   * 파라미터명 
		//   - form요청 (input 태그의 name 속성 지정한 파라미터명)
		//   - URL의 쿼리스트링의 파라미터 localhost:8080/~~~?title=제목&writer=작성자&content=내용
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		int boardNo = Integer.parseInt( request.getParameter("boardNo") );
		
		Board board = new Board();
		board.setBoardNo(boardNo);
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		int result = boardDAO.update(board);
		
		System.out.println("title : " + title);
		System.out.println("writer : " + writer);
		System.out.println("content : " + content);
		System.out.println("boardNo : " + boardNo);
		return "redirect:/board/read?boardNo=" + boardNo;
	}
	
	/**
	 * 게시글 수정 - 처리		POST		/board/update
	 * - 수정할 (게시글번호/제목/작성자/내용) 요청파라미터로 전달 받음
	 * - 게시글 번호를 조건으로 게시글 정보 수정 처리
	 * - 게시글 읽기로 리다이렉트 → /board/read.html 응답
	 * @param request
	 * @return
	 */
	@PostMapping("/board/update")
	public String updatePost(Board board) {
		// 요청 파라미터명과 일치하는 변수명을 가지고 있는 객체를 사용하여 여러 파라미터를 가져올 수 있다.
		int boardNo = board.getBoardNo();
		String title = board.getTitle();
		String writer = board.getWriter();
		String content = board.getContent();
		
		int result = boardDAO.update(board);
		
		System.out.println("title : " + title);
		System.out.println("writer : " + writer);
		System.out.println("content : " + content);
		System.out.println("boardNo : " + boardNo);
		return "redirect:/board/read?boardNo=" + boardNo;
	}
	
	/**
	 * 게시글 삭제 - 처리		DELETE	/board/delete
	 * - 게시글 번호를 요청파라미터로 전달 받음
	 * - 게시글 번호를 조건으로 게시글 정보를 삭제 처리
	 * - 게시글 목록으로 리다이렉트 → /board/list.html 응답
	 * @param boardNo
	 * @return
	 */
	@DeleteMapping("/board/delete")
	public String delete(int boardNo) {
		// 게시글 번호로 게시글 삭제
		System.out.println("boardNo : " + boardNo);
		
		int result = boardDAO.delete(boardNo);
		
		return "redirect:/board/list";
	}
	
	
	/**
	 * 게시글 삭제 - 처리		POST	/board/delete
	 * - 게시글 번호를 요청파라미터로 전달 받음
	 * - 게시글 번호를 조건으로 게시글 정보를 삭제 처리
	 * - 게시글 목록으로 리다이렉트 → /board/list.html 응답
	 * @param boardNo
	 * @return
	 */
	@PostMapping("/board/delete")
	public String deletePost(int boardNo) {
		// 게시글 번호로 게시글 삭제
		System.out.println("boardNo : " + boardNo);
		
		int result = boardDAO.delete(boardNo);
		
		return "redirect:/board/list";
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
