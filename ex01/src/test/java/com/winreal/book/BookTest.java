package com.winreal.book;

import static org.junit.Assert.assertNotNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winreal.board.boardTest;
import com.winreal.mapper.BookMapper;
import com.winreal.vo.BoardVO;
import com.winreal.vo.BookVO;
import com.winreal.vo.Criteria;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class BookTest {
	
	@Autowired
	BookMapper bookMapper;
	
	@Test
	public void getList() {
		//assertNotNull(bookMapper);
		Criteria cri = new Criteria();
		cri.setSearchField("title");
		cri.setSearchWord("제목");
		
		List<BookVO> list = bookMapper.getList(cri);
		log.info(list);
		}

	@Test
	public void getTotalCnt() {
		int res = bookMapper.getTotalCnt(new Criteria());
		System.out.println("총건수 : " + res);
		
	}
	
	@Test
	public void getOne() {
		BookVO board = bookMapper.getOne(11);
		System.out.println(board);
	}
	
	
	
}
