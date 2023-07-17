package com.winreal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winreal.mapper.LogMapper;
import com.winreal.vo.LogVO;

@Service
public class LogServiceImpl implements LogService{
	
	@Autowired
	LogMapper logMapper;

	public int insert(LogVO vo) {
		return logMapper.insert(vo);
	}
}
