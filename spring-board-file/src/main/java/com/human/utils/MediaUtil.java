package com.human.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

/**
 * 확장자로 컨텐트타입 매핑
 * @author h
 *
 */
public class MediaUtil {
	
	private static Map<String, MediaType> mediaType;
	
	static {
		mediaType = new HashMap<String, MediaType>();
		mediaType.put("JPG", MediaType.IMAGE_JPEG);
		mediaType.put("JPEG", MediaType.IMAGE_JPEG);
		mediaType.put("GIF", MediaType.IMAGE_GIF);
		mediaType.put("PNG", MediaType.IMAGE_PNG);
	}
	
	// 확장자로 컨텐트타입을 확인하는 메소드
	public static MediaType getMediaType(String ext) {
		return mediaType.get(ext.toUpperCase());
	}

}
















