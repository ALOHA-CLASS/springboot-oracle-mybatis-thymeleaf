package com.human.board.util;

import java.sql.*;

public class JDBConnection {
	
	public Connection con;
	public Statement stmt;
	public PreparedStatement psmt;
	public ResultSet rs;
	// 기본 생성자
	public JDBConnection() {
		try {
			// JDBC 드라이버 로드
			// MySQL
			// Class.forName("com.mysql.cj.jdbc.Driver");
			
			// Oracle
			Class.forName("oracle.jdbc.OracleDriver");
			
			// DB 에 연결
			// URL:[PORT]/[스키마]?옵션파라미터
			// MySQL
			// String url = "jdbc:mysql://localhost:3306/human?serverTimezone=Asia/Seoul&allowPublicKeyRetrieval=true&useSSL=false";
			
			// Oracle
			// jdbc:oracle:thin  		- JDBC 드라이버 타입 (thin 타입)
			// localhost				- 호스트 주소 (IP 주소),  localhost 또는 127.0.0.1 은 현재 PC의 IP 를 가리킨다.
			// :1521					- 포트번호  (1521은 오라클 DB서버의 기본 포트 번호이다.)
			// :xe  또는 :orcl			- SID 	  (서비스 ID)
			//String url ="jdbc:oracle:thin:@localhost:1521:xe"; // 11g
			String url ="jdbc:oracle:thin:@localhost:1521:orcl"; // 12c이상
			String id = "human";
			String pw = "123456";
			
			con = DriverManager.getConnection(url, id, pw);
			con.setAutoCommit(false);		// AUTO COMMIT 기능 비활성화
			
			System.out.println("DB 연결 성공");
		}
		catch(Exception e) {
			System.out.println("DB 연결 실패");
			e.printStackTrace();
		}
	}
	
}








