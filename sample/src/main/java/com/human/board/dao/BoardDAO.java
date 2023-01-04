package com.human.board.dao;

import java.sql.SQLException;
import java.sql.Savepoint;
import java.util.LinkedList;
import java.util.List;

import com.human.board.domain.Board;
import com.human.board.util.JDBConnection;



public class BoardDAO extends JDBConnection {
	
	
	// 데이터 목록 조회
	public List<Board> selectList() {
		
		LinkedList<Board> boardList = new LinkedList<>();
		
		String sql = " SELECT * "
				   + " FROM board "
				   + " ORDER BY reg_date DESC";
		try {
			stmt = con.createStatement();		// 쿼리문 생성
			rs = stmt.executeQuery(sql);		// 쿼리 실행
			
			while( rs.next() ) {
				Board board = new Board();
				board.setBoardNo( rs.getInt("board_no") );
				board.setTitle( rs.getString("title") );
				board.setContent( rs.getString("content") );
				board.setWriter( rs.getString("writer") );
				board.setRegDate( rs.getTimestamp("reg_date") );
				board.setUpdDate( rs.getTimestamp("upd_date") );
				
				boardList.add(board);
			}
		} catch (Exception e) {
			System.out.println("게시글 목록 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
		return boardList;
	}
	
	
	
	// 데이터 조회
	public Board select(int board_no) {
		
		Board board = new Board();
		
		String sql = " SELECT * "
				   + " FROM board"
				   + " WHERE board_no = ? ";
		try {
			psmt = con.prepareStatement(sql);		// 쿼리문 생성
			psmt.setInt(1, board_no);				// 파라미터 매핑
			rs = psmt.executeQuery();				// 쿼리 실행
			
			if( rs.next() ) {
				board.setBoardNo( rs.getInt("board_no") );
				board.setTitle( rs.getString("title") );
				board.setContent( rs.getString("content") );
				board.setWriter( rs.getString("writer") );
				board.setRegDate( rs.getTimestamp("reg_date") );
				board.setUpdDate( rs.getTimestamp("upd_date") );
			}
			else {
				System.out.println(board_no + "번의 게시글이 존재하지 않습니다.");
				board = null;
			}
			
		} catch (Exception e) {
			System.out.println("게시글 조회 시, 예외 발생");
			e.printStackTrace();
		}
		
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
		
		Savepoint savepoint = null;
		try {
			// 롤백하기 위한 SavePoing 설정
			savepoint = con.setSavepoint("insertSavePoint");
			psmt = con.prepareStatement(sql);		// 쿼리문 생성
			psmt.setString(1, board.getTitle());
			psmt.setString(2, board.getWriter());
			psmt.setString(3, board.getContent());
			result = psmt.executeUpdate();			// SQL 실행 요청
			
			// 데이터 1개 이상 처리, COMMIT
			if( result > 0 ) {
				con.commit();
			}
			// executeQuery()  	: 데이터 조회
			// executeUpdate()  : 데이터 추가/수정/삭제  -- 적용된 행(데이터)의 수를 반환
			
		} catch (SQLException e) {
			try {
				con.rollback(savepoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("게시글 등록 시, 예외 발생");
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	// 데이터 수정
	public int update(Board board) {
		
		int result = 0;
		String sql = " UPDATE board "
				   + " SET title = ? "
				   + "    ,writer = ? "
				   + "    ,content = ? "
				// + "    ,upd_date = now() "    		//- mysql
				   + "    ,upd_date = sysdate "       	//  - oracle
				   + " WHERE board_no = ? ";
		
		Savepoint savepoint = null;
		try {
			// 롤백하기 위한 SavePoing 설정
			savepoint = con.setSavepoint("UpdateSavePoint");
			psmt = con.prepareStatement(sql);		// 쿼리문 생성
			psmt.setString(1, board.getTitle());
			psmt.setString(2, board.getWriter());
			psmt.setString(3, board.getContent());
			psmt.setInt(4, board.getBoardNo());
			result = psmt.executeUpdate();			// SQL 실행 요청
			
			if( result > 0 ) {
				con.commit();
			}
			// executeQuery()  	: 데이터 조회
			// executeUpdate()  : 데이터 추가/수정/삭제  -- 적용된 행(데이터)의 수를 반환
			
		} catch (SQLException e) {
			try {
				con.rollback(savepoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("게시글 수정 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}
	
	
	// 데이터 삭제
	public int delete(int board_no) {
		
		int result = 0;
		String sql = " DELETE FROM board "
				   + " WHERE board_no = ? ";
		
		Savepoint savepoint = null;
		try {
			// 롤백하기 위한 SavePoing 설정
			savepoint = con.setSavepoint("DeleteSavePoint");
			psmt = con.prepareStatement(sql);		// 쿼리문 생성
			psmt.setInt(1, board_no );
			result = psmt.executeUpdate();			// SQL 실행 요청
			
			if( result > 0 ) {
				con.commit();
			}
			// executeQuery()  	: 데이터 조회
			// executeUpdate()  : 데이터 추가/수정/삭제  -- 적용된 행(데이터)의 수를 반환
			
		} catch (SQLException e) {
			try {
				con.rollback(savepoint);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("게시글 삭제 시, 예외 발생");
			e.printStackTrace();
		}
		return result;
	}

}
























