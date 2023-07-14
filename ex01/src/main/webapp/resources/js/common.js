console.log('common.js');
//개발자모드 > 네트워크탭 > 파일잘다운받았는지 확인가능

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