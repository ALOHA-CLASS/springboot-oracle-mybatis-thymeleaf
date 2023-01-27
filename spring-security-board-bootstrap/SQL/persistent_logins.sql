-- 자동 로그인 토큰 테이블
CREATE TABLE persistent_logins (
	username 	VARCHAR(64) NOT NULL,	-- 사용자 아이디
	series		VARCHAR(64) NOT NULL,	-- 초기 식별자
	token		VARCHAR(64) NOT NULL,	-- 자동 로그인 시, 변경되는 토큰
	last_used	TIMESTAMP	NOT NULL,	-- 마지막으로 토큰이 갱신된 날짜
	PRIMARY KEY (series)
);
