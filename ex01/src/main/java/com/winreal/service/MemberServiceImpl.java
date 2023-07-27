package com.winreal.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import com.fasterxml.jackson.databind.ObjectMapper;
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
			// 반환 전 사용자의 권한 조회해서 멤버에 추가!
			if(res) {
				//사용자 권한을 조회
				member.setRole(memberMapper.getMemberRole(member.getId()));
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
	
	@Autowired
	ApiExamMemberProfile apiExam;
	
	@Override
	public void naverLogin(HttpServletRequest request, Model model) {
		try {
			
		// callback처리 -> access_token 얻을 수 있따
		Map<String, String> callbackRes = callback(request);
		
		String access_token = callbackRes.get("access_token");
		//access_token -> 사용자 정보조회
		Map<String, Object> responseBody 
							= apiExam.getMemberProfile(access_token);
		
		Map<String, String> response
							= (Map<String, String>) responseBody.get("response");
		System.out.println("========================naverLogin");
		System.out.println(response.get("name"));
		System.out.println(response.get("id"));
		System.out.println(response.get("gender"));
		System.out.println("========================naverLogin");

		//(멤버를) 세션에 저장
		model.addAttribute("id", response.get("id"));
		model.addAttribute("name", response.get("name"));
		model.addAttribute("gender", response.get("gender"));
	} catch (Exception e){
		e.printStackTrace();
	}
}

	public Map<String, String> callback(HttpServletRequest request) throws Exception{
		 //변수선언
	    String clientId = "QGbdayimqNZE2X3_0n4V";//애플리케이션 클라이언트 아이디값";
	    String clientSecret = "1wkGib1dgM";//애플리케이션 클라이언트 시크릿값";
	    String code = request.getParameter("code");
	    String state = request.getParameter("state");
	    try {
	    String redirectURI = URLEncoder.encode("http://localhost:8080/login/naver_callback", "UTF-8");
	    String apiURL;
	    
	    //url경로생성
	    apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
	    apiURL += "client_id=" + clientId;
	    apiURL += "&client_secret=" + clientSecret;
	    apiURL += "&redirect_uri=" + redirectURI;
	    apiURL += "&code=" + code;
	    apiURL += "&state=" + state;
	    String access_token = "";
	    String refresh_token = "";
	    System.out.println("apiURL="+apiURL);
	      URL url = new URL(apiURL);
	      HttpURLConnection con = (HttpURLConnection)url.openConnection();
	      con.setRequestMethod("GET");
	      int responseCode = con.getResponseCode();
	      BufferedReader br;
	      System.out.print("responseCode="+responseCode);
	      if(responseCode==200) { // 정상 호출
	        br = new BufferedReader(new InputStreamReader(con.getInputStream()));
	      } else {  // 에러 발생
	        br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
	      }
	      String inputLine;
	      StringBuffer res = new StringBuffer();
	      while ((inputLine = br.readLine()) != null) {
	        res.append(inputLine);
	      }
	      br.close();
	      if(responseCode==200) {
	        System.out.println("token요청" + res.toString());  //요청결과값  > 맵으로 변환해서 반환
	        
	        //json문자열을 Map으로 반환
	        Map<String, String> map = new HashMap<String, String>();
	        ObjectMapper objectMapper = new ObjectMapper();
	        map = (Map<String, String>) objectMapper.readValue(res.toString(), Map.class);
	        return map;
	      } else {
	    	  throw new Exception("callback 반환 코드 : " + responseCode);
	      }
	      
	    } catch (Exception e) {
	      System.out.println(e);
	      throw new Exception("callback 처리 중 예외사항이 발생하였습니다.");	      
	    }
	}
	
	}

