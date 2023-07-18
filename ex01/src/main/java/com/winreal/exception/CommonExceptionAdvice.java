package com.winreal.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.extern.log4j.Log4j;


/**
 * root-context에 component-scan을 패키지에 적용해야 빈으로 등록!
 * 
 * ● ControllerAdvice
 * 컨트롤러에 대한 예외를 처리하는 객체임을 명시
 * 컨트롤러가 실행중 발생되는 오류이므로 500번 오류가 발생하는 경우 실행됩니다
 * 
 * ● ExceptionHandler
 * Controller, RestController가 적용된 Bean내에서 발생하는 예외를 
 * 하나의 메서드에서 처리해주는 기능을 한다.
 * 
 */

@ControllerAdvice
@Log4j
/*★log4j 오류발생 
	java.lang.Error: Unresolved compilation problems: 
	org.apache.log4j cannot be resolved to a type
	org.apache.log4j.Logger cannot be resolved to a type
	Logger cannot be resolved to a type
=> pom.xml에서 <scope>runtime</scope>를 주석처리 해주면 된다
*/
public class CommonExceptionAdvice {

	// ex을 매개변수로 받고 모델에 값을 저장
	@ExceptionHandler(Exception.class)
	public String except(Exception ex, Model model) {
		System.out.println("Exception...." + ex.getMessage());
		ex.printStackTrace();
		log.info("Exception....");
		log.debug("로그테스트 + debug");
		log.error("로그테스트 + error");
		
		
		// 화면에 가져다 출력하고 싶으면 모델에 담아주면 된다(이름과 값주면 됨)
		model.addAttribute("exception", ex);
		
		
		return "/error/error500";
	}
	
	//404에러를 처리할 수 있게끔 메서드 만듦
	//NoHandlerFoundException가 실행될 수 있는 처리를 web.xml에 해야함
	@ExceptionHandler(NoHandlerFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public String handle404(NoHandlerFoundException ex) {
		
		return "/error/error404";
	}
}
