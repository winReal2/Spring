<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib  prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
//form을 submit하는 go함수
	function go(page){
		document.searchForm.pageNo.value = page;
		//alert(document.searchForm.pageNo.value);
		document.searchForm.submit();
	}
</script>


</head>
<body>

<!--
${pageDto }
${pageDto.startNo }/${pageDto.endNo }
 -->

<!-- 페이지 블럭 생성 -->
<nav aria-label="Page navigation example">

  <ul class="pagination justify-content-end">
    <%-- li class="page-item disabled"> <!-- disabled : 버튼에 대한 비활성화 처리, pageDto에서 boolean처리해둠 --> --%>
    <li class="page-item ${pageDto.prev?'':'disabled' }">
      <a class="page-link" 
      			onclick="go(${pageDto.startNo-1})"
      			href="#">Previous</a>
    </li>
    
    <!-- 반복문 이용 -->
    <c:forEach begin="${pageDto.startNo }" 
    			end="${pageDto.endNo }" 
    			var="i">
    			
    <li class="page-item">
    	<!-- 페이지 넘버가 i와 같으면 active처리 (삼항연산자 이용) -->
    	<a class="page-link ${pageDto.cri.pageNo == i? 'active' : '' }" 
    		onclick="go(${i})" 
    		href="#"> ${i } </a>
   	</li>
    </c:forEach>
    
    <li class="page-item ${pageDto.next?'':'disabled' }">
    	<a class="page-link"
    		onclick="go(${pageDto.endNo+1})" 
      		href="#">Next</a>
    </li>
  </ul>
</nav>



</body>
</html>