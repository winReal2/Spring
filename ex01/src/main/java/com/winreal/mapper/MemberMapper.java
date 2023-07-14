package com.winreal.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Select;

import com.winreal.vo.Member;

public interface MemberMapper {

	public Member login(Member member);

	public int idCheck(Member member);

	public int insert(Member member);
	
	
}
