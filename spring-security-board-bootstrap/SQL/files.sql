CREATE TABLE files (
	
	file_no 		NUMBER			NOT NULL		-- 파일 번호
   ,parent_table	VARCHAR2(100)	NOT NULL		-- 참조 테이블		
   ,parent_no		NUMBER			NOT NULL		-- 참조 번호
   ,file_name		VARCHAR2(200)	NOT NULL		-- 파일 명
   ,file_path		VARCHAR2(2000)	NOT NULL		-- 파일 경로
   ,reg_date		DATE DEFAULT sysdate	NOT NULL	--	 등록일자
   ,upd_date		DATE DEFAULT sysdate	NOT NULL	--	 수정일자
  , CONSTRAINT files_pk PRIMARY KEY (file_no)

);

