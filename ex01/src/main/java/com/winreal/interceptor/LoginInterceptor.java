package com.winreal.interceptor;

import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


//컨트롤러가 요청을 받았을때 로그인되어있는지 아닌지 판단하고 로그인된 사용자만 접근할 수 있도록 처리
/**
 * Spring Interceptor
 * 		HTTP 요청 처리 과정에서 요청을 가로채고 처리 전후에 추가 작업을 수행
 * 		인터셉터는 컨트롤러에 진입하기 전, 컨트롤러 실행 후, 뷰 렌더링 전 등 
 * 		다양한 시점에서 사용하여 요청의 처리흐름을 제어하거나 조작할 수 있습니다.
 * 		(로그인에 많이 사용)
 * 
 * 		인증 및 권한 체크로직을 작성해봅시다.
 * @author user
 *
 */
@Component
public class LoginInterceptor implements HandlerInterceptor{

	/**
	 * 로그인이 되어있지 않은 사용자인 경우 로그인 페이지로 이동
	 * 
	 * preHandle : 컨트롤러 실행 전 실행
	 * postHandle : 컨트롤러 실행 후 실행
	 * return : 리턴타입  (boolean)
	 * 			true(요청컨트롤러 실행)
	 * 			false(요청컨트롤러 실행하지 않음)
	 */
	//리퀘스트가져와서 세션에 아이디가 들어있는지 확인
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("userId") != null
			&& !session.getAttribute("userId").equals("")){
			return true;
		} else {
			String msg = URLEncoder.encode("로그인 후 사용가능한 메뉴입니다.", "utf-8");
		
			//로그인이 되어있지 않은 사용자인 경우 로그인 페이지로 이동
			//요청컨트롤러 받아들일 수 없으니 다른데로가! 이때 중요한게 한글처리(인터셉터에선 자동으로 한글처리 안해줌)
			response.sendRedirect("/login?msg=" + msg);
			return false;
		}
		
	}

	
}
