package com.winreal.vo;

import lombok.Data;

@Data
public class FileuploadVO {
	
	private String uuid;
	private String uploadPath;
	private String fileName;
	private String filetype;
	private int bno;
	
	// 저장된 파일경로
	private String savePath;
	private String s_savePath;

	//uploadpath + uuid + "_" + fileName;
	
	//썸네일관리 시 경로추가 등 필요한 것들 만들면 된다

}