package com.human.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Service;

import com.human.domain.UserAuth;
import com.human.domain.Users;
import com.human.mapper.UserMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private AuthenticationManager authenticationManager;

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

	// 토큰 생성 후, 인증 처리
	@Override
	public HttpSession tokenAuthenticaion(Users user, HttpServletRequest request) throws Exception {
		String username = user.getUserId();
		String password = user.getUserPwChk();			// userPw 는 암호화 되기 때문에, userPwChk 를 사용
		log.info("username : " + username);
		log.info("password : " + password);
		
		HttpSession session = request.getSession();
		
		// 아이디, 패스워드로 인증토큰 생성
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken( username, password );
		
		// 토큰에 요청정보를 등록
		token.setDetails( new WebAuthenticationDetails(request) );
		
		// 토큰을 이용하여 인증 (로그인)
		Authentication authentication = authenticationManager.authenticate(token);
		
		User authUser = (User) authentication.getPrincipal();
		log.info("인증된 사용자 아이디: " + authUser.getUsername());
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return session;
	}
	
	

}










