package com.winreal.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.winreal.vo.Member;

public interface MemberMapper {
	public Member login(Member member);
	public int insert(Member member);
	public int idCheck(Member member);

	public List<String> getMemberRole(String id);
	
	
}
