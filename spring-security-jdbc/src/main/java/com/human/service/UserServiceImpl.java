package com.human.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.human.domain.UserAuth;
import com.human.domain.Users;
import com.human.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public int join(Users user) throws Exception {
		
		// 패스워드 암호화
		String plainPw = user.getUserPw();
		String encodedPw = passwordEncoder.encode(plainPw);
		user.setUserPw(encodedPw);
		
		int result = mapper.join(user);			// 회원 등록
		
		String userId = user.getUserId();
		// 권한 등록
		if( result > 0 ) {
			UserAuth userAuth = new UserAuth();
			userAuth.setUserId( userId );
			userAuth.setAuth("ROLE_USER");		
			mapper.insertAuth(userAuth);		// 권한 등록
		}
		
		return result;
	}
	
	

}










