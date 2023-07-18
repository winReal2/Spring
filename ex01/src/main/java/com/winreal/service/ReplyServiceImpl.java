package com.winreal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.winreal.mapper.BoardMapper;
import com.winreal.mapper.ReplyMapper;
import com.winreal.vo.Criteria;
import com.winreal.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	
	@Autowired
	BoardMapper boardMapper;
	
	
	@Override
	public List<ReplyVO> getList(int bno, Criteria cri) {
		return replyMapper.getList(bno, cri);
	}

	//@Transactional : 두개 다 성공해야 커밋, 하나라도 실패하면 롤백
	/**
	 * Transactional
	 * 		서비스 로직에 대한 트랜잭션 처리를 지원
	 * 		오류발생시 롤백
	 */
	@Transactional
	@Override
	public int insert(ReplyVO vo) {
		// 댓글 입력시  Board 테이블의  댓글 수(replyCnt)를  1 증가시켜줍니다.
		// 둘 중 하나라도 실패 : 롤백, 둘다성공시 커밋! 
		//(@Transactional 붙이고 안붙이고의 차이가 크다)
		boardMapper.updateReplyCnt(vo.getBno(), 1);   //삭제할땐 -1
		return replyMapper.insert(vo);
	}

	@Transactional
	@Override
	public int delete(int rno) {
		//얘는 3번의 과정을 거침 : 조회 > 업뎃 > 삭제
		ReplyVO vo = replyMapper.getOne(rno);
		boardMapper.updateReplyCnt(vo.getBno(), -1); 
		
		return replyMapper.delete(rno);
	}

	@Override
	public int update(ReplyVO vo) {
		return replyMapper.update(vo);
	}
	
	public int totalCnt(int bno) {
		return replyMapper.totalCnt(bno);
	}
	
}
