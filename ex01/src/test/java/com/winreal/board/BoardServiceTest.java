package com.winreal.board;

import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winreal.service.BoardService;
import com.winreal.vo.BoardVO;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BoardServiceTest {
	
	@Autowired
	BoardService boardService;
	
	public void getListXml() {
		List<BoardVO> list = boardService.getListXml(null, null);
		
		list.forEach(board->{
			log.info(board);
		});
		
	}
}
