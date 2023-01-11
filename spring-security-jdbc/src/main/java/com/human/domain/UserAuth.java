package com.human.domain;

import java.time.LocalDateTime;

import lombok.Data;

/**
 * 회원권한
 * @author h
 *
 */
@Data
public class UserAuth {
	
	private int authNo;			// 권한번호
	private String userId;		// 회원 아이디
	private String auth;		// 권한

}

