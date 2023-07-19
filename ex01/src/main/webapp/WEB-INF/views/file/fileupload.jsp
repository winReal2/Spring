<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<script type="text/javascript">
	window.addEventListener('load', function(){
		//리스트 조회
		btnList.addEventListener('click', function(){
			getFileList();
		})
		
		//파일업로드(rest방식)
		btnFileupload.addEventListener('click', function(){
			// 웹개발에서 HTML 폼 데이터를 
			// javaScript로 쉽게 조작하고 전송하는 방법을 제공하는 API입니다. (formData검색 > mdn사이트 접속 > entris)
			 // form데이터라는 객체에 담아서 
			let formData = new FormData(fileuploadForm);  //form name (fileuploadForm)
			//formData에 직접 데이터 추가하고 싶을떄
			formData.append('name', 'winreal');
			
			console.log("formData : ", formData);
			// FormData값 확인
			for(var pair of formData.entries()){
				//타입을 확인
				if(typeof(pair[1]) == 'object'){
					let fileName = pair[1].name;
					let fileSize = pair[1].fileSize;
					
					// 파일 확장자, 크기 체크
					// 서버에 전송가능한 형식인지 확인 (1)
					// 최대 전송가능한 용량을 초과하지 않는지 확인 (2) 
					
					if(!checkExtension(fileName, fileSize)){
						return false;
					}
					console.log('fileName', fileName);
					console.log('fileSize', fileSize);
				
				}
				
			}
			/*
			//form에 있는데이터 수집해서 보내준다
			fetch('/file/fileuploadAction'
					, {
						method : 'post' 
						, body : formData
					})
				.then(response => response.json())
				.then(map => (map) =>{
					console.log(map)
					}) 
			*/		
					
			fetch('/file/fileuploadActionFetch'
					, {
						method : 'post' 
						, body : formData
					})
					//요청결과 json문자열을 javascript 객체로 반환
				.then(response => response.json())
				//콜백함수 실행
				.then(map => fileuploadRes(map)); 		
					
		});
	})
	
	function checkExtension(fileName, fileSize){
		let maxSize = 1024 * 1024 * 10;
		// .exe, .sh, .zip, .alz 로 끝나는($, 끝맺음) 문자열
		// 정규표현식 : 특정규칙을 가진 문자열을 검색하거나 치환할 때 사용
		let regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
		if(maxSize <= fileSize){
			alert("파일 사이즈 초과");
			return false;
		}  
		//문자열에 정규식 패턴을 만족하는 값이 있으면 true, 없으면 false를 리턴한다.
		if(regex.test(fileName)){
			alert("해당 종류의 파일은 업로드할 수 없습니다.");
			return false;
		}
		
		return true; //위의 과정을 다 거치고 나서는 true리턴
	}
	
	function fileuploadRes(map){
		if(map.result == 'success'){
			divFileuploadRes.innerHTML = map.msg;
		}
	}

	function getFileList(){
		///file/list/{bno}
		let bno = document.querySelector("#bno").value;
		fetch('/file/list/' + bno)
					.then(response => response.json())
					.then(map => viewFileList(map));
	}
	
	function viewFileList(map){
		console.log(map);
		let content = '';
		if(map.list.length > 0){
		map.list.forEach(function(item, index){
			content += item.fileName + '<br>';  // 복잡하니까 삭제 + '/' + item.savePath +'<br>';  //리스트 undefined 오류 > filename을  fileName로!!  
		})
		} else {
			content = "등록된 파일이 없습니다.";
		}
		divFileupload.innerHTML = content;
	}

</script>
</head>
<body>


	<h2>파일 업로드</h2>
	<form method="post" enctype="multipart/form-data"
			action="/file/fileuploadAction" name="fileuploadForm">
	
	<h2>파일선택</h2>
	<%--input type="file" name="files" multiple="multiple"><!-- 요즘 많이 사용하지만 브라우저 버전에 영향을 받음 -->	 --%>	
	bno : <input type="text" name="bno" id="bno" value="50"><br>
	<input type="file" name="files"><br>		
	<input type="file" name="files"><br>		
	<input type="file" name="files"><br><br>		

	<button type="submit">파일업로드</button>
	<!-- rest방식으로 파일업로드 -->
	<button type="button" id="btnFileupload">Fetch 파일업로드</button>
	
	
	<div id="divFileuploadRes">
	res : ${param.msg }  <!-- jsp페이지 내에서만 el표현식 사용할 수 있다 (개발자모드 > 네트워크 > 응답에서 확인가능  -->
	</div>
	
	</form>
	
	<h2>파일 리스트 조회</h2>
	<button type="button" id="btnList">리스트 조회</button>
	<div id="divFileupload"></div>
	
</body>
</html>