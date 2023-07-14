package com.winreal.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.winreal.mapper.MemberMapper;
import com.winreal.vo.Member;

//어노테이션 붙여줘야함!
@Service 
public class MemberServiceImpl implements MemberService {

	@Autowired
	MemberMapper memberMapper;

	@Autowired
	BCryptPasswordEncoder encoder;
	
	@Override
	public Member login(Member paramMember) {
		//사용자 정보 조회
		Member member = memberMapper.login(paramMember);
		if(member != null) {
			//사용자가 입력한 비밀번호가 일치하는지 확인
			//사용자가 입력한 비밀번호, 데이터베이스에 암호화되어 저장된 비밀번호를 넣어준다
			boolean res = encoder.matches(paramMember.getPw(), member.getPw());
			
			// 비밀번호 인증이 성공하면 member객체를 반환
			if(res) {
				return member;
			}
		}
		
		return null;
	}


	@Override
	public int insert(Member member) {
		// BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		// 비밀번호 암호화
		// 입력된 비밀번호(getPw)를 암호화해서 다시 비밀번호에 넣어줬어요(setPw)
		member.setPw(encoder.encode(member.getPw()));
		System.out.println("pw : " + member.getPw());
		return memberMapper.insert(member);
	}


	@Override
	public int idCheck(Member member) {
		return memberMapper.idCheck(member);
	}

	}

