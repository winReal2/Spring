package com.winreal.interceptor;

import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.winreal.vo.Member;

@Component
public class AdminInterceptor implements HandlerInterceptor{
	//컨트롤러 실행전 어드민이 맞는지 체크
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		//널체크 후 형변환
		if(session.getAttribute("member") != null) {
			Member member = (Member)session.getAttribute("member");
			List<String> role = member.getRole();
			
			if(role.contains("ADMIN ROLE")) {
				return true;
			}
			
			
		}
		
		//로그인 페이지로 이동(할 수 있도록 해줌)
		String msg = URLEncoder.encode("로그인 후 사용가능한 메뉴입니다.", "utf-8");
		response.sendRedirect("/login?msg=" + msg);
		
		return false;
	
	}
	
}
