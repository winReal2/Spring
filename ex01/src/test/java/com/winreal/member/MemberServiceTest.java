package com.winreal.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.winreal.board.BoardServiceTest;
import com.winreal.mapper.MemberMapper;
import com.winreal.service.MemberService;
import com.winreal.vo.Member;

import lombok.extern.log4j.Log4j;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/main/webapp/WEB-INF/spring/root-context.xml")
@Log4j
public class MemberServiceTest {
	
	//테스트 하려면 먼저 객체 생성해야함
	@Autowired
	MemberService memberService;
	
	@Test
	public void test() {
		Member member = new Member();
		member.setId("admin");
		member.setPw("1234");
		member.setName("admin");
		
		
		member = memberService.login(member);
		
		log.info(member);
		assertNotNull(member);
	}
	
	@Test
	public void testInsert() {
		Member member = new Member();
		member.setId("test5");
		member.setPw("1234");
		member.setName("name2");
		
		int res = memberService.insert(member);
		
		assertEquals(1, res);
	}
	
	@Test
	public void testIdCheck() {
		Member member = new Member();
		member.setId("test2");
		
		int res = memberService.idCheck(member);
		System.out.println("결과 : " + res);
	}
}
