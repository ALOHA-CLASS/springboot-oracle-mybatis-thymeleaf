package com.human.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.domain.Files;
import com.human.mapper.FileMapper;

@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileMapper fileMapper;

	@Override
	public int insert(Files file) throws Exception {
		return fileMapper.insert(file);
	}

	@Override
	public List<Files> listByParentNo(Files file) throws Exception {
		return fileMapper.listByParentNo(file);
	}

	@Override
	public Files read(int fileNo) throws Exception {
		return fileMapper.read(fileNo);
	}

	
	
}
