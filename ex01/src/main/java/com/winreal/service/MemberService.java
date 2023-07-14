package com.winreal.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.winreal.vo.Member;


@Service
public interface MemberService {
	
	Member login(Member member); 
	
	int insert(Member member);
	
	int idCheck(Member member);
	
}
