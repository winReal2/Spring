package com.winreal.controller;

import java.net.URL;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.winreal.service.MemberService;
import com.winreal.vo.Member;

import lombok.extern.apachecommons.CommonsLog;


@Controller
public class MemberController extends CommonRestController{
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
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "login";
	}
	
	//메서드 이름보다 어노테이션이 더 중요
	/*
	 * json형식의 데이터를 주고 받고 싶음
	 * 페이지를 갱신하지 않고 원하는 데이만 요청하기 위해 어노테이션 사용
	 * */
	@PostMapping("/loginAction")
	public @ResponseBody Map<String, Object> loginAction(
										@RequestBody Member member
											, Model model
											, HttpSession session) {
		System.out.println("id" + member.getId());
		System.out.println("pw" + member.getPw());
		
		member = service.login(member);
		// model.addAttribute("message", member.getId() + "환영합니다.");
		// 컨트롤러에서는 화면으로 갈수있도록 처리
		
		if(member != null) {
			session.setAttribute("member", member);
			session.setAttribute("userId", member.getId());
			Map<String, Object> map = responseMap(REST_SUCCESS, "로그인 되었습니다.");
			
			//★사용자 화면으로 갈지, 관리자 화면으로갈지 url 만듦
			if(member.getRole() != null && member.getRole().contains("ADMIN ROLE")) {
				//관리자 로그인 => 관리자 페이지로 이동 
				map.put("url", "/admin");   //★login.jsp이동후 로그인시 이동하는 페이지 수정  (function loginCheck)
			} else {
				map.put("url", "/board/list");				
			}
			System.out.println("map =============== " + map);
			//return responseMap(1, "로그인");
			//return responseMap(REST_SUCCESS, "로그인 되었습니다.");
			return map;
		} else {
			//return responseMap(0, "로그인");
			return responseMap(REST_FAIL, "아이디와 비밀번호를 확인해주세요.");
		}
	}	
	
	@PostMapping("/idCheck")
	public @ResponseBody Map<String, Object> idCheck(@RequestBody Member member){
		
		int res = service.idCheck(member);
		// count = 1  실패 (아이디가 있으면 회원가입 못함)
		// insert, update, delete > 0  : 트루
		
		if(res == 0) {
			return responseMap(REST_SUCCESS, "사용가능한 아이디 입니다");
		} else {
			return responseMap(REST_FAIL, "이미 사용중인 아이디 입니다");			
		}
	}	
	
	@PostMapping("/register")
	public @ResponseBody Map<String, Object> register(@RequestBody Member member){
		
		//오류날 수 있으니 try-catch
		try {
			int res = service.insert(member);
			return responseWriteMap(res);
			
		} catch (Exception e) {
			e.printStackTrace();
			return responseMap(REST_FAIL, "등록중 예외사항이 발생했습니다.");
					
		}
	}
}





