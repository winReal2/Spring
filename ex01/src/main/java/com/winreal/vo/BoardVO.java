package com.winreal.vo;

import lombok.Data;

@Data
public class BoardVO {
	//빈으로 생성시 private 해주는게 좋다(세터,게터로 접근 가능하게)
	private int bno;
	private String title;
	private String content;
	private String writer;
	private String regDate;
	private String updateDate;
	
}


// 데이터 조회후 매퍼를 이용해 출력