package com.winreal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.winreal.mapper.BookMapper;
import com.winreal.vo.BookVO;
import com.winreal.vo.Criteria;
import com.winreal.vo.PageDto;

import lombok.extern.log4j.Log4j;

@Service
@Log4j
public class BookServiceImpl implements BookService {

	@Autowired
	BookMapper bookMapper;
	
	@Override
	public List<BookVO> getList(Criteria cri, Model model) {
		/*
		 * 1. 리스트 조회
		 * 2. 총건수 조회
		 * 3. 페이지DTO 생성(페이지블럭을 생성)
		 * */
		
		List<BookVO> list = bookMapper.getList(cri);
		int totalCnt = bookMapper.getTotalCnt(cri);
		//게시물의 총건수로 페이지 블럭을 생성
		PageDto pageDto = new PageDto(cri, totalCnt);
		
		model.addAttribute("list", list);
		model.addAttribute("pageDto", pageDto);
		log.info("pageDto : " + pageDto);
		
		return null;
	}

	@Override
	public List<BookVO> getList() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	public BookVO getOne(int no, Model model) {
		BookVO book = bookMapper.getOne(no);
		model.addAttribute("book", book);
		return book;
	}

	@Override
	public BookVO getOne(int no) {
		// TODO Auto-generated method stub
		return null;
	}



	
}
