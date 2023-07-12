package com.winreal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.winreal.service.ReplyService;
import com.winreal.vo.Criteria;
import com.winreal.vo.PageDto;
import com.winreal.vo.ReplyVO;

import lombok.extern.log4j.Log4j;
import oracle.jdbc.proxy.annotation.Post;

/**
 * RestController
 * 		Controller가 REST 방식을 처리하기 위한 것임을 명시합니다. 
 * 
 */
@RestController
@Log4j
public class ReplyController {

	@Autowired
	ReplyService service;

	/**
	 * PathVariable
	 * 		URL 경로에 있는 값을 파라미터로 추출하려고 할 때 사용 
	 * 		경로의 일부분을 파라메터로 사용
	 * 
	 * 		URL 경로의 일부를 변수로 사용
	 * 		{}중괄호로 꼭 묶어줘야 사용가능!
	 *	/reply/list/83
	 * @return
	 */
	@GetMapping("/reply/list/{bno}/{page}")//뷰페이지에 페이지넘버가 이미사용되고 있어서 페이지로
	public Map<String, Object> getList(@PathVariable("bno") int bno
								,@PathVariable("page") int page){
		// 종류에 상관없이 담을 수 있음
		Map<String, Object> map = new HashMap<String, Object>();
		
		log.info("bno : " + bno);
		log.info("page : " + page);
		
		Criteria cri = new Criteria();
		cri.setPageNo(page);
		
		//페이지 처리(시작번호~ 끝번호) 
		List<ReplyVO> list = service.getList(bno, cri);
		int totalCnt = service.totalCnt(bno);
		//페이지 블럭을 생성
		PageDto pageDto = new PageDto(cri, totalCnt);
		
		map.put("list", list);
		map.put("pageDto", pageDto);
		
		return map;
	}
	
	@GetMapping("/reply/delete/{rno}")
	public Map<String, Object> delete(@PathVariable("rno") int rno){
		Map<String, Object> map = new HashMap<String, Object>();
		int res = service.delete(rno);
		if(res > 0) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
			map.put("message", "댓글 삭제중 예외사항이 발생 하였습니다.");
		}
		return map;
	}
	
	
	/**
	 * RequestBody
	 * 		JSON 데이터를 원하는 타입으로 바인딩 처리
	 * 		(파라메터 자동수집해서 vo객체)
	 * @param vo
	 * @return
	 */
	@PostMapping("/reply/insert")
	public Map<String, Object> insert(@RequestBody ReplyVO vo){
		log.info("================= insert");
		log.info("replyVO" + vo);
		Map<String, Object> map = new HashMap<String, Object>();
		
		try {
			int res = service.insert(vo);		
			
			if(res>0) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");
				map.put("message", "댓글 등록중 예외사항이 발생 하였습니다.");
			}
			
		} catch (Exception e) {
			map.put("result", "fail");
			map.put("message", e.getMessage());
		}
		
		return map;
	}
	
	
	@PostMapping("/reply/editAction")
	public Map<String, Object> update(@RequestBody ReplyVO vo){
		Map<String, Object> map = new HashMap<String, Object>();
		
		int res = service.update(vo);
		if(res>0) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
			map.put("message", "댓글 등록중 예외사항이 발생 하였습니다.");
		}
		
		return map;
	}
	
	
	
	
}



















