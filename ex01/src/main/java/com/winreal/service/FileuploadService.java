package com.winreal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winreal.vo.FileuploadVO;

@Service
public interface FileuploadService {
	
	List<FileuploadVO> getList(int bno);

	int insert(FileuploadVO vo);
}
