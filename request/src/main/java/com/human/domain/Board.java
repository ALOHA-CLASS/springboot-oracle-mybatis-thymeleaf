package com.human.domain;

import java.util.Date;

import lombok.Data;

// lombok 라이브러리 기능
// - @Data : 클래스의 getter, setter, toString 등의 메소드 자동정의
@Data
public class Board {
	private int boardNo;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private Date updDate;
}
