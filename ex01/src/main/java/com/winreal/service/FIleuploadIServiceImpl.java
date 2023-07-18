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

}
