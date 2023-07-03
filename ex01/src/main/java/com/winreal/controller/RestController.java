package com.winreal.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.winreal.vo.Member;

/**
 * JSON데이터를 반환하는 방법
 * ajax통신 할 때 사용할거라 기억해두기!
 * 
 * 1.라이브러리 추가
 * : jackson-databind 라이브러리를 pom.xml 파일에 추가
 * 
 * 2.리턴타입에 어노테이션 추가
 * : 매서드 선언부의 리턴타입에 ResponseBody 어노테이션을 추가합니다.
 *   리턴타입에 맞게 데이터를 자동으로 변환해줍니다!
 *   
 *   개발자 도구 > 네트워크 탭 > 응답헤더 확인!
 *   content-type이 변경된 것을 확인 할 수 있음
 *   text/html => application/json
 * 
 * 3.메서드의 리턴타입
 * 	 VO, DTO
 * 	 JSON타입의 데이터를 만들어서 반환하는 용도로 사용
 *  
 * @author user
 *
 */
@Controller
public class RestController {
	//댓글! 데이터주고받아서 화면에 출력
	//json 타입을 반환
	
	@GetMapping("rest")
	public @ResponseBody Member rest(Member member) {
		return member;  //member 객체를 반환하고 있음
	}
	//결과 : 키와 값의 형태로 구성되어 있음	
	//{"id":null,"pw":null,"name":null,"age":0,"dueDate":null}	
	//jackson-databind 라이브러리를 추가해야 json형식으로 파싱가능
	//메이븐 리파지토리 > 검색 > 첫번째 꺼 > 첫번째 버전 > 복붙(pom.xml)	
		
	
/**
 *	 ● ResponseEntity
 * 	  헤더정보를 가공하기 위한 용도로 사용
 * 
 *   request, response 객체를 직접 다루지 않고
 *   스프링MVC에서 제공해주는 어노테이션 또는 객체를 이용합니다.
 */
	@GetMapping("restResponseEntity")	
	public ResponseEntity<String> rest1(){
		HttpHeaders header = new HttpHeaders();
		header.add("content-type", "application/json;charset=utf-8");
		
		String msg = "{\"name\":\"모모\"}";
		ResponseEntity<String> rs = new ResponseEntity<String>(msg, header, HttpStatus.OK);
		
		return rs;
	}
}
