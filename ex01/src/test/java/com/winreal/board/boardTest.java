package com.winreal.board;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winreal.mapper.BoardMapper;
import com.winreal.vo.BoardVO;
import com.winreal.vo.Criteria;

import lombok.extern.log4j.Log4j;





@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class boardTest {
	
	@Autowired
	BoardMapper boardMapper;

	@Test
	public void getList() {
		assertNotNull(boardMapper);
		
		List<BoardVO> list = boardMapper.getList();
		
		list.forEach(board -> {
			log.info("boardVO===========");
			log.info(board.getBno());
			log.info(board.getTitle());
			log.info(board.getContent());
		});
		 
	}
	
	@Test
	public void getListXml() {
		assertNotNull(boardMapper);
		List<BoardVO> list = boardMapper.getListXml(new Criteria());
		 log.info(list);
		 
		list.forEach(board -> {
			log.info("boardVOXml===========");
			log.info(board.getBno());
			log.info(board.getTitle());
			log.info(board.getContent());
		});
		
	}
	
	@Test
	public void insert() {
		BoardVO board = new BoardVO();
		board.setTitle("제목");
		board.setContent("내용");
		board.setWriter("글쓴이");
		
		int res = boardMapper.insert(board);
		
		assertEquals(res, 1);
	}
	
	@Test
	public void insertSelectKey() {
		BoardVO board = new BoardVO();
		board.setTitle("제목 selectkey");
		board.setContent("내용");
		board.setWriter("글쓴이");
		
		int res = boardMapper.insertSelectKey(board);
		log.info("=========================");
		log.info("bno : " + board.getBno());
		assertEquals(res, 1);
		
	}
	
	@Test
	public void getOne() {
		BoardVO board = boardMapper.getOne(2);
		System.out.println("=================");
		log.info(board);
	}
	
	@Test
	public void delete() {
		int res = boardMapper.delete(2);
		assertEquals(res, 0);
	}
	
	@Test
	public void update() {
		int bno = 10;
		
		BoardVO board = new BoardVO();
		board.setBno(bno);
		board.setTitle("제목 수정수정");
		board.setContent("내용");
		board.setWriter("글쓴이");
		
		int res = boardMapper.update(board);
		
		BoardVO getBoard = boardMapper.getOne(bno);
		
		assertEquals("제목 수정수정", getBoard.getTitle());
	}

	@Test
	public void getTotalCnt() {
		int res = boardMapper.getTotalCnt(new Criteria());
		log.info("totalCnt : " + res);
	}
	
	@Test
	public void updateReplyCnt() {
		int res = boardMapper.updateReplyCnt(83, 1);
		assertEquals(1, res);
	}
}





