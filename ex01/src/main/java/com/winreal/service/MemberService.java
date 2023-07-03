package com.winreal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.winreal.dao.MemberDao;
import com.winreal.vo.Member;


@Service
public class MemberService {
	
	//주입은 어노테이션으로
	@Autowired
	MemberDao dao;
	
	public Member login(Member paramMember, Model model) {
		Member member = dao.login(paramMember);
		//메세지처리를 서비스에서 하고 싶어
		if(member == null) {
			model.addAttribute("message", "아이디/비밀번호를 확인해주세요");
		} else {
			model.addAttribute("message", member.getName() + "님 환영합니다.");
		}
		return member;
		
	}
}
