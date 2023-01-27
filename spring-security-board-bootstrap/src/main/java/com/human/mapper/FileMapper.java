
package com.human.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.human.domain.Files;

@Mapper
public interface FileMapper {
	
	// 파일 등록
	public int insert(Files file) throws Exception;
	
	// 파일 목록
	public List<Files> listByParentNo(Files file) throws Exception;
	
	// 파일 조회
	public Files read(int fileNo) throws Exception;
	
	// 파일 삭제
	public int delete(int fileNo) throws Exception;
	
}









