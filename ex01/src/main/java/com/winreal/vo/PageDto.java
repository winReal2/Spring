package com.winreal.vo;

import lombok.Data;

@Data
public class PageDto {

	private Criteria cri;	//페이지 번호, 페이지당 게시물수
	private int total;		//총 게시물의 수
	
	private int startNo;	//페이지 블록의 시작번호
	private int endNo;		//페이지 블록의 끝번호
	
	//버튼에 대한 활성화 처리 (이전블럭, 다음블럭으로 갈수있는지에 대해)
	private boolean prev, next;  //이전, 다음 버튼 활성(true)/비활성(false)
	
	private int realEnd;	//페이지 끝번호
	
	//이거 두개만 받으면 페이지 처리된다
	public PageDto(Criteria cri, int total){
		this.cri = cri;
		this.total = total;
		
		//페이지 블럭의 끝 번호 구하기 (공식처럼 외우기)
		this.endNo = (int)(Math.ceil(cri.getPageNo()/5.0) * 5);
		//페이지 블럭의 시작번호
		this.startNo = this.endNo - 4;
		
		//총 게시물의 수를 페이지당 보여지는 게시물의 수로 나눠서 실제 끝 페이지 번호를 구함
		realEnd = (int)(Math.ceil((total*1.0)/cri.getAmount()));
		endNo = endNo > realEnd ? realEnd : endNo;
		
		//false면 비활성
		prev = startNo > 1 ? true : false;
		next = endNo == realEnd ? false : true;
	}
}
