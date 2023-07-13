package com.winreal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.winreal.mapper.MemberMapper;
import com.winreal.vo.Member;
import com.winreal.vo.MemberList;

//어노테이션 붙여줘야함!
@Service 
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;


	@Override
	public Member login(Member member, Model model) {
		return memberMapper.login(member);
	}
		
		
		
	}

