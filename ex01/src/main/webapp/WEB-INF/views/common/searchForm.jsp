<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="/board/list" 
			method="get" name="searchForm">
<!-- 			
	${pageDto}
	${pageDto.cri.pageNo }
	${pageDto.cri.searchField}
	${pageDto.cri.searchWord}
 -->			
		<!-- 일단 눈에 보이게 -->
		<input type="text" name="bno" value="${pageDto.cri.searchWord}"> <!-- 검색어 유지 -->
		<input type="text" name="pageNo" value="${pageDto.cri.pageNo }">
		
	<div 	class="row g-3 justify-content-center"> <!-- 가운데 정렬을 위해 div박스 하나 만듦 -->
	  <div class="col-sm-2" >
		<select name="searchField" class="form-select" 
				aria-label="Default select example">
		  <option value="title" ${pageDto.cri.searchField == 'title'? 'selected' :''}>제목</option>
		  <option value="content" ${pageDto.cri.searchField == 'content'? 'selected' :''}>내용</option>
		  <option value="writer" ${pageDto.cri.searchField == 'writer'? 'selected' :''}>작성자</option>
		</select>
	  </div>
	  <div class="col-sm-5">
	    <label for="searchWord" class="visually-hidden"></label>
	    <input name="searchWord" type="text" class="form-control" 
	    		id="searchWord" placeholder="검색어"
	    		value="${pageDto.cri.searchWord}">
	  </div>
	  <div class="col-sm-3">
	    <button type="submit" class="btn btn-primary mb-3 w-100" onclick="go(1)">검색</button>  <!-- onclick="go(1)"로 검색시 1페이지로 -->
	  </div>
  	</div>
	</form>

</body>
</html>