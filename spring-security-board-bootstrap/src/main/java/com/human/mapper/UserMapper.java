package com.human.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.human.domain.UserAuth;
import com.human.domain.Users;

@Mapper
public interface UserMapper {
	
	// 회원 등록
	public int join(Users user) throws Exception;
	
	// 권한 등록
	public int insertAuth(UserAuth userAuth) throws Exception;
	

}
