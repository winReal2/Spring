package com.winreal.vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

/**
 * Lombok 라이브러리
 * getter/setter, equals, toString 등의 메서드를 자동생성해준다.
 * 
 * Data 어노테이션
 * IDE(이클립스, STS)에 설치 후 롬복라이브러리를 추가후 사용가능
 * IDE에 설치가 되어 있지 않으면 어노테이션을 추가해도 메서드가 생성되지 않을 수 있습니다.
 * Outline View를 통해 메서드가 생성되었는지 확인해주세요!
 *
 */

//롬복 라이브러리 - 세터게터 자동생성 또는 생성자 자동생성
@Data
public class Member {
	// private 오류가 나지는 않지만 직접 접근을 막는다
	private String id;
	private String pw;
	private String name;
	private int age;
	
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date dueDate;
}
