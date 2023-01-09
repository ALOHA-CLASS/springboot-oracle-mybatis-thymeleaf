package com.human.service;

import java.util.List;

import com.human.domain.Board;

public interface BoardService {

	// 게시글 목록
	public List<Board> list() throws Exception;
}
