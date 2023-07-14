package com.winreal.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.winreal.vo.PageDto;

public class CommonRestController {

	private final String REST_WRITE = "등록";
	private final String REST_EDIT = "수정";
	private final String REST_DELETE = "삭제";
	private final String REST_SELECT = "조회";
	protected final String REST_SUCCESS = "success";
	protected final String REST_FAIL = "fail";
	
	
	/**
	 * 입력, 수정, 삭제의 경우 int값을 반환합니다.
	 * 결과를 받아서 Map을 생성후 반환합니다.
	 * 
	 * @return
	 */
	// 클래스에 만들어 놓고 클래스를 상속받아서 사용할 수 있게끔 하고 싶음 (원래 ReplyController에 있던 메서드)
	// map을 생성후 result, msg 세팅 (result에 의해 메세지가 세팅되고 있음)
	// 결과값 받아와서 메세지 넣어준다
	
	public Map<String, Object> responseMap(int res, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
		//처리결과를 맵에 담아주고 싶음
		if(res > 0) {
			map.put("result", REST_SUCCESS);
			map.put("msg", msg + "되었습니다.");
		} else {
			map.put("result", REST_FAIL);
			map.put("msg", msg + "중 예외가 발생하였습니다.");
		}
		
		//map.put("msg", msg);
		return map;
	}
	
	
	//사용자는 메서드만 부르면 메세지를 넣을 일이 없음
	public Map<String, Object> responseWriteMap(int res){
		return responseMap(res, REST_WRITE);
	}
	public Map<String, Object> responseEditMap(int res){
		return responseMap(res, REST_EDIT);
	}
	public Map<String, Object> responseDeleteMap(int res){
		return responseMap(res, REST_DELETE);
	}
	
	
	// list에 ? 넣어주면 형변환 상관없는 
	public Map<String, Object> responseListMap(List<?> list, PageDto pageDto){
				
		int res = list !=null ? 1 : 0;
		Map<String, Object> map = responseMap(res, REST_SELECT);
		
		map.put("list", list);
		map.put("pageDto", pageDto);
		
		return map;
	}
	
	public Map<String, Object> responseMap(String result, String msg){
		Map<String, Object> map = new HashMap<String, Object>();
		
		map.put("result", result);
		map.put("msg", msg);
		
		return map;
	}
}



















