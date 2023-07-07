package com.winreal.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.winreal.vo.BookVO;
import com.winreal.vo.Criteria;

@Service
public interface BookService {
	//구현메서드 없는 메서드 : 추상메서드(미완성)
	public List<BookVO> getList();

	public List<BookVO> getList(Criteria cri, Model model);

	public BookVO getOne(int no);

}
