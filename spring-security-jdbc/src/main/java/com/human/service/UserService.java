package com.human.service;

import com.human.domain.Users;

public interface UserService {

	// 회원 등록
	public int join(Users user) throws Exception;
	
}
