package com.winreal.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.winreal.vo.Criteria;
import com.winreal.vo.ReplyVO;

@Service
public interface ReplyService {
	
	List<ReplyVO> getList(int bno, Criteria cri);
	
	int insert(ReplyVO vo);
	
	int delete(int rno);

	int update(ReplyVO vo);
	
	int totalCnt(int bno);
}
