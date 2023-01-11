package com.human.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableWebSecurity			// 해당 클래스를 스프링 시큐리티 설정 클래스로 지정  
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// 스프링 시큐리티 설정
		log.info("스프링 시큐리티 설정...");
		
		// authentication (인증)
		// : 등록된 사용자인지 확인하여 입증 
		
		// authorization (인가)
		// : 사용자의 권한을 확인하여 권한에 따라 자원의 사용범위를 구분하여 허락하는 것
		
		// 인가 처리
		http.authorizeHttpRequests()			// 인가 설정
			// antMatchers("자원 경로") 		- 인가에 대한 URL 경로 지정
			// permitAll()			 		- 모든 사용자에 허용
			// hasAnyRole("USER", "ADMIN")	- 여러 권한에 대하여 허용
			// hasRole("ADMIN")				- 단일 권한에 대하여 허용
			.antMatchers("/").permitAll()			// "/" 경로는 모든 사용자에 허용
			.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")	
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()			// 요청에 대하여 인증된 사용자만 허용
			;
		
		
		// 로그인 설정
		http.formLogin()		
			.loginPage("/auth/login")			// 사용자 지정 로그인 페이지 경로 (기본 경로 : /login)
			.loginProcessingUrl("/auth/login")	// 사용자 지정 로그인 처리 경로   (기본 경로 : /login)
			.permitAll()		// 로그인 폼 url 경로는 모든 사용자에 허용
			;
		
		// 로그아웃 설정
		http.logout()							// 로그아웃 기본 경로 : /logout
			.logoutUrl("/auth/logout")			// 사용자 지정 로그아웃 처리 경로 (기본 경로 : /logout)
			.invalidateHttpSession(true)		// 로그아웃 처리 후, 세션을 무효화
			.logoutSuccessUrl("/")				// 로그아웃 성공 시, 이동할 페이지 경로
			.permitAll()						// 로그아웃 url 경로는 모든 사용자에 허용
			;
		
		
		// CSRF (Cross Site request forgery)
		// - 사이트 간 요청 위조하는 공격
		// - 인증된 사용자가 자신의 의미와 무관하게 웹사이트를 공격하도록 만드는 것
		// SSL 
		// - 보안서버, 브라우저에서 로그인한 개인정보를 암호화하여 전송하는 기능이 있는 서버
		// - 해당 사이트에서 로그인한 정보라는 것을 인증

		// CSRF 방지 기능 비활성화		(SSL 설정이 되어있지 않아서, 403 에러발생하기 때문에)
		// http.csrf().disable();
		
		
	}

	// 인증 관리 설정 메소드
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		// AuthenticationManagerBuilder : 인증 관리 객체
		auth.inMemoryAuthentication()			// 인증방식 : 인메모리 방식으로 지정
			
			// passwordEncoder.encode("비밀번호")  : 비밀번호를 암호화하는 메소드
			// 사용자 등록 - 아이디 / 패스워드 / 권한
			.withUser("user").password( passwordEncoder.encode("123456") ).roles("USER")
			.and()
			// 관리자 등록
			.withUser("admin").password( passwordEncoder.encode("123456") ).roles("ADMIN")
			;
	}
	
	
	

}






















