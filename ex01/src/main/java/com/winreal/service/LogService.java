package com.winreal.service;

import org.springframework.stereotype.Service;

import com.winreal.vo.LogVO;

@Service
public interface LogService {

	
	public int insert(LogVO vo);
}
