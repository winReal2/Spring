package com.winreal.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winreal.board.BoardServiceTest;
import com.winreal.mapper.LogMapper;
import com.winreal.vo.LogVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class LogTest {
	
	@Autowired
	LogMapper logMapper;

	@Test
	public void test() {
		LogVO vo = new LogVO();
		
		
		vo.setClassName("className");
		vo.setMethodName("methodName");
		vo.setErrmsg("errmsg");
		vo.setParams("params");
		
		int res = logMapper.insert(vo);
		
		System.out.println("res : " + res);
	}
}
