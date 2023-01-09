package com.human.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.human.domain.Board;


@Mapper
public interface BoardMapper {
	
	public List<Board> list() throws Exception;

}
