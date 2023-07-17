package com.winreal.member;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;

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
public class MemberTest {
	//테스트 하려면 먼저 객체 생성해야함
	@Autowired
	MemberMapper memberMapper;
	
	@Test
	public void test() {
		Member member = new Member();
		member.setId("admin");
		member.setPw("1234");
		
		
		member = memberMapper.login(member);
		
		log.info(member);
		assertNotNull(member);
	}
	
	@Test
	public void testInsert() {
		Member member = new Member();
		member.setId("test1");
		member.setPw("1234");
		member.setName("name1");
		
		int res = memberMapper.insert(member);
		member = memberMapper.login(member);
		
		assertEquals(1, res);
	}
	
	@Test
	public void testIdCheck() {
		Member member = new Member();
		member.setId("test1");
		
		int res = memberMapper.idCheck(member);
		member = memberMapper.login(member);
		
		assertEquals(1, res);
	}
	
	@Test
	public void testGetMemberRole() {
		List<String > list= memberMapper.getMemberRole("admin");
		System.out.println(list);
		//list.contains : 리스트 안에 이 문자열("ADMIN ROLE")이 있는지 확인해서 트루폴스 반환
		System.out.println("관리자 권한 : " + list.contains("ADMIN ROLE"));
	}
}



