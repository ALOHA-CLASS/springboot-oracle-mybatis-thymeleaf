package com.human.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.human.security.CustomLoginSuccessHandler;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableWebSecurity				// 해당 클래스를 스프링 시큐리티 설정 클래스로 지정  
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true) //시큐리티 어노테이션 활성화		
public class SecurityConfig extends WebSecurityConfigurerAdapter  {
	
	// 비밀번호 암호화 객체
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	// 데이터 소스 객체
	@Autowired
	private DataSource dataSource;
	
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
			.antMatchers("/board/**").permitAll()
			.antMatchers("/auth/**").permitAll()
			.antMatchers("/user/**").hasAnyRole("USER", "ADMIN")	
			.antMatchers("/admin/**").hasRole("ADMIN")
			.anyRequest().authenticated()			// 요청에 대하여 인증된 사용자만 허용
			;
		
		
		// 로그인 설정
		http.formLogin()		
			.loginPage("/auth/login")			// 사용자 지정 로그인 페이지 경로 (기본 경로 : /login)
			.loginProcessingUrl("/auth/login")	// 사용자 지정 로그인 처리 경로   (기본 경로 : /login)
			.successHandler( authenticationSuccessHandler() )	// 로그인 성공 처리
			.permitAll()						// 로그인 폼 url 경로는 모든 사용자에 허용
			;
		
		// 로그아웃 설정
		http.logout()							// 로그아웃 기본 경로 : /logout
			.logoutUrl("/auth/logout")			// 사용자 지정 로그아웃 처리 경로 (기본 경로 : /logout)
			.invalidateHttpSession(true)		// 로그아웃 처리 후, 세션을 무효화
			.logoutSuccessUrl("/")				// 로그아웃 성공 시, 이동할 페이지 경로
			.permitAll()						// 로그아웃 url 경로는 모든 사용자에 허용
			;
		
		
		// 자동 로그인
		// - 한 번 로그인하면, 
		//   브라우저 종료 후 다시 접속하여도 아이디/비밀번호 입력없이 자동으로 로그인하는 기능이다.
		// - persistent_logins (자동 로그인 토큰 테이블)을 정의해야한다.
		http.rememberMe()
			.key("human")
			// DataSource 가 등록된 PersistentRepository 토큰저장 정보 등록
			.tokenRepository( tokenRepository() )	
			// 토큰 유효기간 설정 (초 단위)
			.tokenValiditySeconds( 60 * 60 * 24  )			// 60x60x24초 = 1일
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
		// JDBC 인증 세팅 정보
		// - 데이터 소스
		// - 아이디/비밀번호/활성여부 를 조회하는 쿼리 (인증처리)
		// - 아이디/권한 을 조회하는 쿼리		   (인가처리)
		// - 비밀번호 암호화방식
		String sql1 = " SELECT user_id as username, user_pw as password, enabled "
				    + " FROM users "
				    + " WHERE user_id = ? ";
		String sql2 = " SELECT user_id as username, auth "
					+ " FROM user_auth"
					+ " WHERE user_id = ? ";
		
		auth.jdbcAuthentication()		// JDBC 방식으로 인증 (데이터베이스에 등록된 사용자 정보로 인증)
			.dataSource( dataSource )	// 데이터 소스 등록
			.usersByUsernameQuery(sql1)
			.authoritiesByUsernameQuery(sql2)
			.passwordEncoder(passwordEncoder);
			
	}
	
	
	// PersistentTokenRepository 객체 생성 메소드
	private PersistentTokenRepository tokenRepository() {
		JdbcTokenRepositoryImpl repositoryImpl = new JdbcTokenRepositoryImpl();
		repositoryImpl.setDataSource(dataSource);
		return repositoryImpl;
	}
	
	// 인증 성공 처리 클래스 - 빈 등록
	@Bean
	public AuthenticationSuccessHandler authenticationSuccessHandler() {
		return new CustomLoginSuccessHandler();
	}

	// 인증 관리자 클래스 - 빈 등록
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	
}






















