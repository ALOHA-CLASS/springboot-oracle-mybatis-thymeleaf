package com.human.controller;

import java.io.File;
import java.io.FileInputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.human.domain.Files;
import com.human.service.FileService;
import com.human.utils.MediaUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {
	
	@Autowired
	private FileService fileService;
	
	/**
	 * 파일 다운로드
	 * @param fileNo
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/{fileNo}")
	public void fileDownload(@PathVariable("fileNo") int fileNo
							,HttpServletResponse response) throws Exception {
		
		// 파일 조회
		Files file = fileService.read(fileNo);
		
		// 파일이 존재하지 않으면,
		if( file == null ) {
			// 응답 상태코드 : 400, 클라이언트의 요청이 잘못되었음을 나타내는 상태코드
			response.setStatus(response.SC_BAD_REQUEST);
			return;
		}
		
		String fileName = file.getFileName();	// 파일 명
		String filePath = file.getFilePath();	// 파일 경로
		
		// 파일 다운로드를 위한 헤더 세팅
		// - ContentType 			: application/octet-straem
		// - Content-Disposition 	: attachment; fileanme="파일명.확장자"
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
		
		
		// 파일 입력
		File downloadFile = new File(filePath);
		FileInputStream fis = new FileInputStream(downloadFile);
		ServletOutputStream sos = response.getOutputStream();

		// 다운로드
		FileCopyUtils.copy(fis, sos);
		
		// 파일을 서버에서 읽어와서, 클라이언트로 출력 → 다운로드
//		byte[] buffer = new byte[1024];  // 1024bytes = 1KB 단위 버퍼
//		int data;
//		while( (data = fis.read(buffer) ) != -1 ) {	// 1KB 씩 파일입력
//			sos.write(buffer, 0, data);		// 1KB 씩 파일출력
//		}
//		fis.close();
//		sos.close();
		
		
	}
	
	/**
	 * 이미지 썸네일 
	 * @param filePath
	 * @param response
	 * @throws Exception
	 */
	@GetMapping("/img")
	public void showImg(String filePath
					   ,HttpServletResponse response ) throws Exception {
		
		log.info("filePath : " + filePath);
		
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		ServletOutputStream sos = response.getOutputStream();
		FileCopyUtils.copy(fis, sos);

		// filePath  : C:/KHM/upload/[UID]_강아지.png
		// 이미지 컨텐트 타입 확인 ( .jpg, .png, .gif, ... )
		String ext = filePath.substring( filePath.lastIndexOf(".") + 1 );	// 확장자
		MediaType mType = MediaUtil.getMediaType(ext);
		
		// 이미지 타입이 아닐 경우
		if( mType == null ) {
			return;
		}
		
		
		log.info("mType : " + mType);
		response.setContentType( mType.toString() );
	}
	
	
	// 파일 삭제
	@PostMapping("/delete")
	public ResponseEntity<String> fileDelete(@RequestBody Files file) throws Exception {
		int fileNo = file.getFileNo();
		log.info("파일 삭제...");
		log.info("fileNo : " + fileNo);
		
		// 파일 삭제 요청
		int result = fileService.delete(fileNo);
		
		
		return new ResponseEntity<String>("SUCCESS", HttpStatus.OK);
	}
	

}

























