package com.winreal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.winreal.vo.BookVO;
import com.winreal.vo.Criteria;

public interface BookMapper {

	public List<BookVO> getList(Criteria cri);
	
	public int getTotalCnt(Criteria cri);
	
	public BookVO getOne(int no);
	
}
