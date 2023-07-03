<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
반갑습니다~!
name : ${name }<br>
age : ${age }<br>
====================
<h3>vo 출력</h3>
${member.name }<br>
${member.age }<br>
${member.dueDate }<br>
${message }<br>
====================
<h3>배열 출력</h3>
${ids } <br>

<!-- 링크로 얘 호출 => /mapping/requestMapping -->
<a href="/mapping/requestMapping">requestMapping 호출</a><br>

<a href="/mapping/getMapping?name=모모&age=30">getMapping 호출</a><br>

<a href="/mapping/getMappingVO?name=모모&age=30&dueDate=2023/07/03">getMappingVO 호출</a><br>

<a href="/mapping/getMappingArr?ids=id1&ids=id2&ids=id3">getMappingArr 호출</a><br>

<a href="/mapping/getMappingList?ids=id1&ids=id2&ids=id3">getMappingList 호출</a><br>

<h3>객체리스트를 파라메터로 전달해봅시다</h3>
	파라메터 전달방법 : 
	list[0].name=momo&list[0].age=123&list[1].name=admin&list[1].age=120<br>

<script>
	function voList(){
		let url = "/mapping/getMappingMemberList"
				+ "?list[0].name=momo&list[0].age=123"
				+ "&list[1].name=admin&list[1].age=120";
		url = encodeURI(url);
		//submit 주석처리 후 console.log(url); 또는
		alert(url);
		location.href=url;
	}
</script>

<!-- a링크가 가지고 있는 기본 이벤트가 실행되지 않도록 #넣어줌 -->
<a href="#" onclick="voList()">객체리스트 전달</a>

<form action="/mapping/requestMapping" method="post">
	<input type="submit" value="전송">
</form>

</body>
</html>