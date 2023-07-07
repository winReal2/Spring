<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

  <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">

 <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/navbar-fixed/">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">
	<link href="/resources/css/style.css" rel="stylesheet">
	
</head>
<body>

<%@include file="../common/header.jsp" %>

<script type="text/javascript">
function requestAction(url){
	viewForm.action=url;
	viewForm.submit();
}
</script>
${param.bno }
${param.searchField}
${param.searchWord}


<main class="container">
  <div class="bg-light p-5 rounded">
    <h1>게시판 (상세보기)</h1>
    <p class="lead">부트스트랩을 이용한 게시판 만들기</p>
    <!-- ★ 상세페이지 > 목록 갔을 때 클릭했던 동일한 페이지로 이동하기 위한 설정!! -->
    <a class="btn btn-lg btn-primary" href="../board/list?pageNo=${param.pageNo }&searchField=${param.searchField}&searchWord=${param.searchWord}" role="button">리스트&raquo;</a>
  </div>
  <p></p>
  <!-- 상세보기 -->
  <div class="list-group w-auto">


<form method="get" name="viewForm" >
	<input type="text" name="bno" value="${board.bno }">
	<div class="mb-3">
	  <label for="title" class="form-label">제목</label>
	  <input name="title" type="text" class="form-control" id="title" readonly value="${board.title }">
	</div>
	<div class="mb-3">
	  <label for="content" class="form-label">내용</label>
	  <textarea class="form-control" id="content" name="content" rows="3" readonly>${board.content }</textarea>
	</div>
	<div class="mb-3">
	  <label for="writer" class="form-label">작성자</label>
	  <input type="text" class="form-control" id="writer" name="writer" readonly value="${board.writer }">
	</div>
	<div class="d-grid gap-2 d-md-flex justify-content-md-center" value="${board.bno }">
	<button type="submit" class="btn btn-primary btn-sm" onclick="requestAction('/board/edit')">수정</button>
	<button type="button" class="btn btn-secondary btn-sm" onclick="location.href='/board/delete?bno=${board.bno}'">삭제</button>
</div>
</form>
</div>
</main>


	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
