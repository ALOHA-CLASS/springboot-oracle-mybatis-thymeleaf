package com.human.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.domain.Board;
import com.human.mapper.BoardMapper;

@Service			// 컨테이너의 빈으로 등록, 비즈니스 로직을 처리하는 서비스로 구분
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper mapper;

	@Override
	public List<Board> list() throws Exception {
		// 처리 로직
		return mapper.list();
	}

	@Override
	public Board read(int boardNo) throws Exception {
		
		return mapper.read(boardNo);
	}

	@Override
	public int insert(Board board) throws Exception {
		
		return mapper.insert(board);
	}

	@Override
	public int update(Board board) throws Exception {
		
		return mapper.update(board);
	}

	@Override
	public int delete(int boardNo) throws Exception {
		
		return mapper.delete(boardNo);
	}


}
