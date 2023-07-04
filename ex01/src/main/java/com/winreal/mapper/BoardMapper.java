package com.winreal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.winreal.vo.BoardVO;

public interface BoardMapper {

	@Select("select * from tbl_board")
	public List<BoardVO> getList();
	
	public List<BoardVO> getListXml();
	
	public int insert(BoardVO board);

	public int insertSelectKey(BoardVO board);
	
	public BoardVO getOne(int bno);
	
	public int delete(int bno);
	
	public int update(BoardVO board);
	
	public int getTotalCnt();
	
//별도의 반환타입이 없는 이유=>select를 제외한 나머지는 int를 반환 (별도로 result타입을 지정하지 않아도됨)
}
