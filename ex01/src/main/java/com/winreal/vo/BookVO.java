package com.winreal.vo;

import lombok.Data;

@Data
public class BookVO {
	private int no;		// 도서 일련번호
	private String title;	// 도서명
	private String author;	// 작가
	
	private String sfile;    // 저장된 파일명
	private String ofile;    // 원본파일명
	
	private String id;		//대여자 ID
	//코드와 값을 함께 가지고 있으면 좋다 (그래서rentyn(코드) rentynStr(값) 추가)
	private String rentyn;	//도서대여 여부
	private String rentynStr;	//decode이용해서 y이면 대여중, x이면 대여가능 이런식으로
	
	
	private String rentno;	//대여번호
	private String startDate;	//대여시작일
	private String endDate;		//반납가능일
	private String returnDate;	//반납일
}
