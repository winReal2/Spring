package com.winreal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.winreal.service.MemberService;
import com.winreal.vo.Member;


@Controller
public class MemberController {
	// 직접 생성하지 않는건 객체지향에서 오류를 발생시키지 않는 방법중 하나
	// 생성자를 만들거나 세터게터 만들어서 받아올수있다
	@Autowired
	MemberService service;
	
	/**
	 * 컨트롤러가 받아서 로그인 페이지로 이동 
	 * 파라미터자동수집
	 * @return
	 */
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	
	//메서드 이름보다 어노테이션이 더 중요
	@PostMapping("/loginAction")
	public String loginAction(Member member, Model model) {
		System.out.println("id" + member.getId());
		System.out.println("pw" + member.getPw());
		
		service.login(member, model);
		// model.addAttribute("message", member.getId() + "환영합니다.");
		// 컨트롤러에서는 화면으로 갈수있도록 처리
		return "main";
	}
	
}
