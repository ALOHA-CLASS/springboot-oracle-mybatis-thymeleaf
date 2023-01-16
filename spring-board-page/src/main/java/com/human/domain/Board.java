package com.human.domain;

import java.time.LocalDateTime;
import java.util.Date;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;


// @Alias("Board")   // 생략하면, 클래스명으로 별칭을 설정한다
@Data
public class Board {
	
	private int boardNo;
	private String title;
	private String writer;
	private String content;
	private Date regDate;
	private Date updDate;
	
	// 첨부파일
	private MultipartFile[] files;
	

}
