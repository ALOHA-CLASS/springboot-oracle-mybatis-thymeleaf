package com.human.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.human.domain.Files;
import com.human.mapper.FileMapper;
import com.human.utils.FileUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private FileUtil fileUtil;

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

	@Override
	public int delete(int fileNo) throws Exception {
		
		// 실제 파일 삭제
		Files file = fileMapper.read(fileNo);
		
		String filePath = file.getFilePath();
		
		boolean result = fileUtil.delete(filePath);
		
		if( result ) {
			return fileMapper.delete(fileNo);
		}
		
		return 0;
	}

	
	
}








