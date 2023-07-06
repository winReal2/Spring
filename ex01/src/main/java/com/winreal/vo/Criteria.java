package com.winreal.vo;

import lombok.Data;

@Data
public class Criteria {
	//source > getter/setter 설정시 클래스 밖에서하면 안된다
	private String searchField; //검색조건
	private String searchWord;	//검색어
	
	int pageNo = 1;		// 요청 페이지 번호
	int amount = 10;	// 한 페이지당 게시물 수
	
	int startNo = 1;	// 시작 페이지블록 번호
	int endNo = 10;		// 끝 페이지 블록번호
	
	
	//페이지 넘버 조회할 떄 startNo, endNo 사용됨
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if(pageNo>0) {
			endNo = pageNo * amount;
			startNo = pageNo * amount - (amount-1);
		}
	}
	
}
