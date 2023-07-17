package com.winreal.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.winreal.vo.Criteria;
import com.winreal.vo.ReplyVO;

public interface ReplyMapper {
	
	/**
	 * 매개변수가 2개 이상 파라메터로 전달되는 경우
	 * Param 어노테이션을 사용
	 * 
	 * @param bno
	 * @param cri
	 * @return
	 */
	
	public List<ReplyVO> getList(@Param(value="bno")int bno
								, @Param(value="cri") Criteria cri);
	
	public int insert(ReplyVO vo);
	
	public int delete(int rno);
	
	public int update(ReplyVO vo);
	
	public int totalCnt(int bno);
	
	public ReplyVO getOne(int rno);
}













