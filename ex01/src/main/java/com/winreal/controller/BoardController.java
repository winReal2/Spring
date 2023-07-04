package com.winreal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.winreal.service.BoardService;
import com.winreal.vo.BoardVO;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.log4j.Log4j;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {
	
	//서비스로부터 리스트 조회
	@Autowired
	BoardService boardService;
	
	
	@GetMapping("list")
	public void getList(Model model) {
		//잘 조회되었는지 확인하려면
		List<BoardVO> list = boardService.getListXml();
		log.info("================");
		log.info(list);
		model.addAttribute("list", list);
	}
	
	@GetMapping("view")
	public void getOne(Model model, int bno) {
		log.info("==============bno" + bno);
		model.addAttribute("board", boardService.getOne(bno));
	}
	
	@GetMapping("write")
	public void write(Model model) {
		
	}
	
	@PostMapping("write")
	public String writeAction(BoardVO board, Model model) {

		log.info(board);
		//WEB-INF/views/ + return + .jsp
		//servlet-context > 뷰리졸버가 앞위로 붙여준다
		//return "/board/write";
		//return "write";  //아무것도 써주지 않아 찾아가지 않음
		
		String msg = "등록되었습니다!";
		model.addAttribute("msg", msg);
		
		return "redirect:/board/list";
		
		
	}
	
}
