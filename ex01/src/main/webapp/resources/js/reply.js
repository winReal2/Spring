
console.log("reply.js============")

//get방식 요청
	function fetchGet(url, callback){
		
		console.log(url);
		console.log(callback);
		
		try{		
			// url 요청
			fetch(url)
				//요청결과 응답받은 json문자열을 javascript 객체로 반환
				.then(response => response.json())
				// 콜백함수 실행
				.then(map => callback(map));
		} catch(e){
			console.log('fetchGet', e);
		}
	}
	
	function fetchPost(url, obj, callback){
		try{		
			// url 요청
			fetch(url
					, {
					  method : 'post'
					, headers : {'Content-Type' : 'application/json'}
					, body : JSON.stringify(obj)
				})
			
				//요청결과 json문자열을 javascript 객체로 반환
				.then(response => response.json())
				// 콜백함수 실행
				.then(map => callback(map));
		} catch(e){
			console.log('fetchPost', e);
		}
	}
	
	//댓글 조회 및 출력               js는 파라메터 있을수도, 없을수도있따
	function getReplyList(page){
		/**
		 * false : false, 0, "", NaN, undefined, null
		 * falsey한 값 이외의 값이 들어있으면 true를 반환
		 * 
		 * page에 입력된 값이 없으면 1로 세팅
		 */
		//페이지가 비어있으면 1로 세팅 (false이면 1로 세팅) 
		if(!page){
			page = 1;			
		}
		
		let bno = document.querySelector('#bno').value;
		console.log('bno : ', bno);
			
		
		//변수를 더하기로 연결하지 않는 방법
		console.log('/reply/list/'+ bno +'/' + page);
		console.log(`/reply/list/${bno}/${page}`);   //백팃이용
		
		// 페이지그려주는 콜백함수 써준다
		//fetch한다는건 서버에 요청. 기다렸다가 요청오면 그 요청으로 다른함수를 실행! 그래서 콜백함수 이름 보내줌
		//함수를 마치 변수처럼 전달할 수 있다
		//url : 요청경로
		//callback : 응답결과를 받아 실행시킬 함수
		fetchGet(`/reply/list/${bno}/${page}`, replyView)
		//★댓글 안나왔던 이유 > 백팃... 
	}
	
	//리스트 결과를 받아서 화면에 출력
		function replyView(map){
			//replyController에 있는 list 불러옴
			let list = map.list;
			let pageDto = map.pageDto;
			console.log(list);
			console.log('pageDto=============', pageDto);
			
				
			
			if(list.length == 0){
				
				replyDiv.innerHTML = '댓글이 없어요'
				
			} else {
				let replyDivStr = 
					
				//replyDiv.innerHTML = '댓글목록'  //innerHTML 나중에
				
					// alt + shift + a 	
					// text-break : 줄바꿈
					 '<table class="table text-break text-center">              '
					+'  <thead>                          '
					+'    <tr>                           '
					+'      <th scope="col" class="col-1">#</th>       '
					+'      <th scope="col" class="col-9">댓글</th>     '
					+'      <th scope="col" class="col-2">작성자</th>    '
					+'    </tr>                          '
					+'  </thead>                         '
					+'  <tbody>                          ';
					
					//리스트가 반복할 대상. 리스트를 돌며 댓글목록을 생성
					//rno로 유일한 행 구분 (index도 유일한 행 구분)
					//수정, 삭제 아이콘 삽입
					list.forEach(reply => {
						replyDivStr += 
						 '  <tr id="tr'+ reply.rno +'" data-value="'+reply.reply+'">         '
						+'    <th scope="row">'+ reply.rno +'</th>      '
						+'      <td  class="text-start">' + reply.reply 
						+' 			<i class="fa-regular fa-pen-to-square" '
						+' 					onclick="replyEdit('+ reply.rno +')"></i> '
						+' 			<i class="fa-solid fa-trash-can" ' 
						+' 					onclick="replyDelete('+ reply.rno +')"></i>'
						+' 		</td> '
						+'      	<td>' + reply.replyer          
						+'      		<br>'+ reply.replydate 
						+'			</td>  '
						+'    	</tr>   ';						
					})
					
					
					replyDivStr += '  </tbody>              '
									+'</table>              ';
					// 화면에 출력
					replyDiv.innerHTML = replyDivStr;
					
					//페이지 블럭 생성
					let pageBlock = 
						 `	<nav aria-label="...">                                            `
						+`  <ul class="pagination justify-content-center">                    `;
					
						// 마침표(세미콜론) 후 다시 변수에 담아준다
						// prev 버튼 (보여지고 안보여지고 처리)
					if(pageDto.prev){
				pageBlock += `    <li class="page-item disabled" onclick="getReplyList(${pageDto.startNo-1})">  `
							+`      <a class="page-link">Previous</a>                             `
							+`    </li>                                                           `;						
					}
						
						//페이지 버튼 (반복문으로 startNo ~ endNo 처리)
					for(let i=pageDto.startNo; i<=pageDto.endNo; i++){
						//활성화
						let active = pageDto.cri.pageNo == i ? 'active': '';
				pageBlock += `    <li class="page-item ${active}" onclick="getReplyList(${i})">	` //쌍따옴표!! 주의!!
							+ ` 	<a class="page-link" href="#"> ${i} </a> 				`
							+ ` 	</li>  `;
					}
						
						// next 버튼 (보여지고 안보여지고 처리)
					if(pageDto.next){
				pageBlock += `    <li class="page-item" onclick="getReplyList(${pageDto.endNo+1})">  `
							+`      <a class="page-link" href="#">Next</a>                        `
							+`    </li>                                                           `;						
					}
						
			pageBlock += `  </ul>                                                             `
						+`</nav>                                                              `;
						
						replyDiv.innerHTML += pageBlock;	
			}
		}
		
	//답글 등록하기
	function replyWrite(){
		//답글 작성시 필요한 데이터 수집  : 답글을 등록하려면 bno, reply, replyer가 있어야 함 
		//로그인한 사용자의 정보를 넣어준다(세션아이디)
		let bno = document.querySelector('#bno').value;
		let reply = document.querySelector('#reply').value;
		let replyer = document.querySelector('#replyer').value;
		
		//전달할 객체로 생성
		let obj = { bno : bno
				, reply : reply
				, replyer : replyer};
		
		console.log(obj);
		
		//url : 요청경로 (어디로 갈건지)
		//obj : JSON형식으로 전달할 데이터 (뭐 지고 갈건지)
		//callback 함수 : 응답결과를 받아서 처리할 함수  (뭘 반환할건지)
		fetchPost('/reply/insert', obj, replyRes);
	}
	
	//답글 등록, 수정, 삭제의 결과를 처리하는 함수
	function replyRes(map){
		console.log(map);
		//성공 : 리스트 조회 및 출력
		if(map.result == 'success'){
			getReplyList();
		} else {
		//실패 : 메세지 출력 (try-catch로 )
			alert(map.message);
		}
		
	}
	
	//답글 삭제하기
	function replyDelete(rno){
		console.log('rno', rno)
		fetchGet('/reply/delete/' + rno, replyRes);
	}
		
	function replyEdit(rno){
		let tr = document.querySelector('#tr' + rno);
		let replyTxt = tr.dataset.value;
		console.log('tr', tr);
		tr.innerHTML = '<td colspan="3">'
						+'<div class="input-group">                        '
						+'  <span class="input-group-text"> 답글수정 </span> '
						+'  <input type="text"                             '
						+'  		 id="reply'+rno+'" value="' + replyTxt + '" '
						+'  		 aria-label="First name"               '
						+'  		 class="form-control">                 '
						+'  <input type="button"                           '
						+'  		 onclick="replyEditAction('+ rno +')"             '
						+'  		 aria-label="Last name"                '
						+'  		 class="input-group-text"              '
						+'  		 value="수정하기">                     '
						+'  </div>										'
						+ '</td>';
	}
		
	function replyEditAction(rno){
		//파라메터 수집 (내가 넘길 파라메터)   /요소를 선택하고 밸류
		let reply = document.querySelector('#reply'+rno).value;
		
		//전송할 데이터를 JS객체로 생성
		let obj = {
				rno : rno
				, reply : reply
		}
		//서버에 요청(콜백함수를 함께 보낸다)
		fetchPost('/reply/editAction', obj, replyRes);
	
	}	
		
		
		
	
		