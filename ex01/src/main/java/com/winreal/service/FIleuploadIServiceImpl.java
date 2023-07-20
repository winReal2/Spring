package com.winreal.service;

import java.util.List;

import com.winreal.mapper.FileuploadMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winreal.vo.FileuploadVO;

@Service
public class FIleuploadIServiceImpl implements FileuploadService {

	@Autowired
	FileuploadMapper mapper;
	
	@Override
	public List<FileuploadVO> getList(int bno) {
		return mapper.getList(bno);
	}

	@Override
	public int insert(FileuploadVO vo) {
		return mapper.insert(vo);
	}

	@Override
	public int delete(int bno, String uuid) {
		//2가지가 이뤄져야함
		//1. 파일삭제
		
		//2. 데이터베이스에서 삭제
		return mapper.delete(bno, uuid); //컨트롤러가 처리가능
	}

}
