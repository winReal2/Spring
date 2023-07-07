package com.winreal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winreal.service.BookService;
import com.winreal.vo.BookVO;
import com.winreal.vo.Criteria;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/book/*")  
public class BookController {
	
	@Autowired //Autowired 어노테이션 없애면 예외발생
	BookService bookService;
	
	//리스트 띄우기 위해메서드 만들어준다
	//반환이 없으면 받은 거 그대로 돌려준다
	@GetMapping("list")	
	public void list(Criteria cri, Model model) {
		//pageNo type이 int -> ''(빈문자열 일때 오류)
		
		log.info("cri : " + cri);
		
		//리스트 조회
		bookService.getList(cri, model);
		
		//화면에 전달
		model.addAttribute("msg", "/book/list");
		// void를 하면 요게 생략 return "/book/list";
		//-> WEB-INF/views/book/list.jsp
	} // 리퀘스트 영역에 저장되서 화면에서 바로 볼 수 있다

	@GetMapping("view")
	public void getOne(BookVO book, Model model) {
		bookService.getOne(book.getNo());
	}
	
	
}












