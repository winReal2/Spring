package com.winreal.mapper;

import java.util.List;

import com.winreal.vo.FileuploadVO;

public interface FileuploadMapper {

	/**
	 * 하나의 게시물에 대해 업로드된 파일 목록을 조회
	 * @param bno
	 * @return
	 */
	//내가 조회하려는 목록은 게시물이 가지고 있는 첨부파일의 목록이기 때문에 bno값을 넣어준다
	public List<FileuploadVO> getList(int bno);
	
	public int insert(FileuploadVO vo);
	
}
