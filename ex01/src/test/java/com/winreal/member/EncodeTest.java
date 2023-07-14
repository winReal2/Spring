package com.winreal.member;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncodeTest {

	/**
	 * 스프링 시큐리티 모듈에서 제공하는 비밀번호 암호화 및 인증 기능
	 * (Spring Security)에서 제공하는 비밀번호를 암호화하는데 사용할 수 있는 메서드를 가진 클래스입니다.
	 * 
	 * pom.xml에 spring security 라이브러리를 추가!
	 */
	BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	public void test() {
		String pw = "안녕하세요";
		
		
		// 암호화할 떄마다 새로운 문자열을 반환하며, 복호화가 불가능! 
		for(int i=0; i<10; i++) {
			
			String encodePw = encoder.encode(pw);
			System.out.println("암호화된 문자열 : " + encodePw);
			
			// 첫 번째 매개변수는 일치여부를 확인하고자 하는 인코딩 되지 않은 패스워드
			// 두 번째 매개변수는 인코딩된 패스워드를 입력합니다.
			// matches의 결과는 boolean타입
			
			// 제출된 인코딩 되지 않은 패스워드(일치여부를 확인하고자 하는 패스워드)와 
			// 인코딩된 패스워드의 일치여부를 확인해준다!
			
			boolean matches = encoder.matches(pw, encodePw);
			System.out.println("인증결과 : " + matches);		
		}
		
//		디비에 저장된 비번과 사용자 입력비번을 매치함수로 일치하는지 확인해야한다
//		인코딩(암호화)된 문자열을 입력해줘야하고
//		회원가입 먼저 만들어야함
		
	}
	//암호화되지 않은 비밀번호는 데이터베이스에서 사용할 수 없음
	@Test
	public void admintest() {
		String pw = "1234";
			
			String encodePw = encoder.encode(pw);
			System.out.println("암호화된 문자열 : " + encodePw);
			
			boolean matches = encoder.matches(pw, encodePw);
			System.out.println("인증결과 : " + matches);		
		}
		
	}


