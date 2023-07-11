package com.winreal.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.winreal.service.BoardService;
import com.winreal.vo.BoardVO;
import com.winreal.vo.Criteria;

import jdk.internal.org.jline.utils.Log;
import lombok.extern.log4j.Log4j;
import oracle.jdbc.proxy.annotation.Post;

@Controller
@RequestMapping("/board/*")
@Log4j
public class BoardController {
	/**
	 *  /board/msg
	 *  WEB_INF/views/board/msg.jsp
	 */
	
	
	// board/reply/test
	@GetMapping("/reply/test")
	public String test() {
		return "/reply/test";
	}
	
	@GetMapping("msg")
	public void msg() {
		
	}
	
	@GetMapping("message")
	public void message(Model model) {
		
	}
	
	@PostMapping("list")
	public void list(Model model) {
		
	}
	
	//서비스로부터 리스트 조회
	@Autowired
	BoardService boardService;
	
	/**
	 * 파라메터의 자동수집
	 *  기본생성자를 이용해서 객체를 생성
	 *  -> setter 메서드를 이용해서 세팅 (Criteria 보면 알수 있다)
	 * @param model
	 * @param cri
	 */
	@GetMapping("list")
	public void getList(Model model, Criteria cri) { //vo 2개 추가 (vo패키지에 2개 생성)
		//잘 조회되었는지 확인하려면
		//List<BoardVO> list = 
				boardService.getListXml(cri, model); //change method 클릭
		log.info("=====================list");
		log.info("cri : " + cri);
		// log.info("list : " + list);

		//model.addAttribute("list", list);
	}
	
	@GetMapping("view")
	public void getOne(Model model, BoardVO paramVO) {
		log.info("======================= bno" + paramVO);
		BoardVO board = boardService.getOne(paramVO.getBno());
		model.addAttribute("board", board);
	}
	
	/**
	 * requestMapping에 /board/가 설정되어 있으므로 
	 * /board/write
	 * jsp, 서블릿, 매핑 등 어떤걸 못찾는건지 오류에서 확인가능
	 * @param model
	 */
	@GetMapping("write")
	public void write(Model model) {
		
	}
	/**
	 * RedirectAttributes
	 * 
	 * 리다이렉트URL의 화면까지 데이터를 전달
	 * 
	 * Model과 같이 매개변수로 받아 사용
	 * addFlashAttribute : 세션에 저장후 페이지 전환
	 * addAttribute : 주소표시줄로 넘어감
	 */
	@PostMapping("write")  //래퍼런스타입의 변수라서 값을 가지고 있따(board)
	public String writeAction(BoardVO board
								, RedirectAttributes rttr
								, Model model) {

		log.info("board : " + board);
		
		//시퀀스 조회 후 시퀀스 번호를 bno에 저장
		int res = boardService.insertSelectKey(board);
		
		String msg = "";
		
		if(res > 0) {
		
			msg = board.getBno() + "번 등록되었습니다!";
			//url?msg=등록(쿼리스트링으로 전달 - 화면에서 param.msg로 받음)
			//rttr.addAttribute("msg", msg);
			
			//세션영역에 저장 -> msg (그냥사용가능)
			//새로고침시 유지되지 않음
			rttr.addFlashAttribute("msg", msg);			
			return "redirect:/board/list";
		
		} else {
			msg = "등록중 예외사항 발생!";
			model.addAttribute("msg", msg);
			return "/board/message";
		}
		//WEB-INF/views/ + return + .jsp
		//servlet-context > 뷰리졸버가 앞뒤로 붙여준다
		//return "/board/write";
		//return "write";  //아무것도 써주지 않아 찾아가지 않음
		// redirect:쓰면 다시 리스트에 대한 매핑을 호출 / 즉 조회하냐 조회하지 않느냐의 차이!
	}
	
	@GetMapping("edit")
	public String edit(BoardVO paramVO, Model model) {
		BoardVO board = boardService.getOne(paramVO.getBno());  // 게시물 정보 조회
		model.addAttribute("board", board);
		
		/**
		 * 수정하기
		 * 글쓰기와 다른점
		 * -bno를 파라메터로 받아야함
		 * -버튼, 버튼의 액션이 달라짐 (그냥 폼 전송하면 글쓰기로 넘어감)
		 */	
		return "/board/write"; //경로반환
	}
	
	@PostMapping("editAction")
	public String editAction(BoardVO board
							, Model model
							, RedirectAttributes rttr) {
		//수정 (이미 서비스에서 구현 해놔서 호출만하면 된다)
		int res = boardService.update(board);
		if(res > 0) {
			//redirect시 request 영역이 공유되지 않으므로 RedirectAttributes를 이용
			//model.addAttribute("msg", "수정되었습니다"); // 메세지를 담기위해 model객체 생성
			rttr.addFlashAttribute("msg", "수정되었습니다..");
			 //addFlashAttribute 세션영역에 잠깐 저장했다가 사라짐
			//상세페이지로 이동
			System.out.println("res : " + res);
			return "redirect:/board/view?bno="+board.getBno(); //bno값 가지고 가야해요 (bno값을 모르니 BoardVO객체에 수집)			
		} else {
			model.addAttribute("msg", "수정중 예외사항이 발생하였습니다");
			//메세지 처리
			return "/board/message";
		}
	}
		@GetMapping("delete")
		public String delete(BoardVO board, Model model, RedirectAttributes rttr) {
			
			int res = boardService.delete(board.getBno());
			if(res > 0) {
				//model.addAttribute("삭제되었습니다..");
				rttr.addFlashAttribute("msg", "삭제되었습니다..");
				return "redirect:/board/list";				
			} else {
				model.addAttribute("msg", "삭제중 예외가 발생하였습니다.");
				return "/board/message";
			}
			
		}
		
		
	}
