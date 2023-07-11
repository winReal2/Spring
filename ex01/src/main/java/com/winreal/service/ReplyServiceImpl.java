package com.winreal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.winreal.mapper.ReplyMapper;
import com.winreal.vo.Criteria;
import com.winreal.vo.ReplyVO;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	ReplyMapper replyMapper;
	
	@Override
	public List<ReplyVO> getList(int bno, Criteria cri) {
		return replyMapper.getList(bno, cri);
	}

	@Override
	public int insert(ReplyVO vo) {
		return replyMapper.insert(vo);
	}

	@Override
	public int delete(int rno) {
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
