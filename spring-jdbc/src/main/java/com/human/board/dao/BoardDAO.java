package com.human.board.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.human.board.domain.Board;


@Repository		// 데이터베이스를 처리하는 빈으로 등록
public class BoardDAO {
	
	// 의존성 자동 주입 - BoardDAO 안에서 JdbcTemplate 를 사용
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	
	// 데이터 목록 조회
	public List<Board> selectList() {
		
		String sql = " SELECT * "
				   + " FROM board "
				   + " ORDER BY reg_date DESC";
		
		List<Board> boardList 
			= jdbcTemplate.query(sql, new BeanPropertyRowMapper<Board>(Board.class));
		
		return boardList;
	}
	
	
	
	// 데이터 조회
	public Board select(int board_no) {
		String sql = " SELECT * "
				   + " FROM board"
				   + " WHERE board_no = ? ";
		
		Board board 
			= jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<Board>(Board.class), board_no);
		
		return board;
	}
	
	
	// 데이터 등록
	public int insert(Board board) {
		
		int result = 0;
		
		// oracle
		String sql = " INSERT INTO board ( board_no, title, writer, content ) "
				   + " VALUES( SEQ_BOARD.nextval, ?, ?, ? ) ";
		/* mysql
		String sql = " INSERT INTO board ( title, writer, content ) "
				   + " VALUES( ?, ?, ? ) ";
		*/
		String title = board.getTitle();
		String writer = board.getWriter();
		String content = board.getContent();
		result = jdbcTemplate.update(sql, title, writer, content );
		
		return result;
	}
	
	
	// 데이터 수정
	public int update(Board board) {
		
		int result = 0;
		String sql = " UPDATE board "
				   + " SET title = ? "
				   + "    ,writer = ? "
				   + "    ,content = ? "
				// + "    ,upd_date = now() "    		//  - mysql
				   + "    ,upd_date = sysdate "       	//  - oracle
				   + " WHERE board_no = ? ";
		
		String title = board.getTitle();
		String writer = board.getWriter();
		String content = board.getContent();
		int boardNo = board.getBoardNo();
		result = jdbcTemplate.update(sql, title, writer, content, boardNo);
		
		return result;
	}
	
	
	// 데이터 삭제
	public int delete(int board_no) {
		
		int result = 0;
		String sql = " DELETE FROM board "
				   + " WHERE board_no = ? ";
		
		result = jdbcTemplate.update(sql, board_no);
		
		
		return result;
	}

}
























