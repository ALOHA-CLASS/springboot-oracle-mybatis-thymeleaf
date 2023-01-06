package com.human.domain;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 회원 정보]
 * - 회원번호
 * - 아이디
 * - 비밀번호
 * - 이름
 * - 이메일
 * - 등록일자
 * - 수정일자
 * @author h
 *
 */

// @Data
@Getter						// getter 메소드 자동생성
@Setter						// setter 메소드 자동생성
@NoArgsConstructor			// 기본 생성자 자동생성
@AllArgsConstructor			// 모든 매개변수를 갖는 생성자 자동생성
@ToString					// toString 메소드 자동생성
public class Member {

	private int memberNo;
	private String userId;
	private String userPw;
	private String name;
	private String email;
	private Date regDate;
	private Date updDate;
	
	
}






