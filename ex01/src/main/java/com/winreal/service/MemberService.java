package com.winreal.service;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.winreal.vo.Member;


@Service
public interface MemberService {
	
	
	public Member login(Member member, Model model); 
		
	
}
