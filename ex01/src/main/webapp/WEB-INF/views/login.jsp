<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!doctype html>
<html lang="ko">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="Mark Otto, Jacob Thornton, and Bootstrap contributors">
    <meta name="generator" content="Hugo 0.104.2">
    <title>Signin Template · Bootstrap v5.2</title>

    <link rel="canonical" href="https://getbootstrap.com/docs/5.2/examples/sign-in/">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">


    <style>
      .bd-placeholder-img {
        font-size: 1.125rem;
        text-anchor: middle;
        -webkit-user-select: none;
        -moz-user-select: none;
        user-select: none;
      }

      @media (min-width: 768px) {
        .bd-placeholder-img-lg {
          font-size: 3.5rem;
        }
      }

      .b-example-divider {
        height: 3rem;
        background-color: rgba(0, 0, 0, .1);
        border: solid rgba(0, 0, 0, .15);
        border-width: 1px 0;
        box-shadow: inset 0 .5em 1.5em rgba(0, 0, 0, .1), inset 0 .125em .5em rgba(0, 0, 0, .15);
      }

      .b-example-vr {
        flex-shrink: 0;
        width: 1.5rem;
        height: 100vh;
      }

      .bi {
        vertical-align: -.125em;
        fill: currentColor;
      }

      .nav-scroller {
        position: relative;
        z-index: 2;
        height: 2.75rem;
        overflow-y: hidden;
      }

      .nav-scroller .nav {
        display: flex;
        flex-wrap: nowrap;
        padding-bottom: 1rem;
        margin-top: -1px;
        overflow-x: auto;
        text-align: center;
        white-space: nowrap;
        -webkit-overflow-scrolling: touch;
      }
      html,
      body {
        height: 100%;
      }

      body {
        display: flex;
        align-items: center;
        padding-top: 40px;
        padding-bottom: 40px;
        background-color: #f5f5f5;
      }

      .form-signin {
        max-width: 330px;
        padding: 15px;
      }

      .form-signin .form-floating:focus-within {
        z-index: 2;
      }

	  .middle{
	    border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
        margin-bottom: -1px;
        
	  }
      .start  {
        margin-bottom: -1px;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
      }
	  
      .end  {
        margin-bottom: 10px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
      }

    </style>


    <script>

      window.addEventListener('load', function(){
			
    	  btnSigninView.addEventListener('click', function(){
          signupForm.style.display='none';
          signinForm.style.display='';
        })

          btnSignupView.addEventListener('click', function(){
          signupForm.style.display='';
          signinForm.style.display='none';
        })
  	
        //로그인
    		btnLogin.addEventListener('click', function(e){
    			//기본이벤트 제거 (서브밋되는거 막아줄 수 있다)
    			e.preventDefault();
    			
    		// 파라메터 수집
    		let obj = {
    				  id : document.querySelector('#id').value
    				, pw : document.querySelector('#pw').value
    		}
    		
    		console.log(obj);
    		
    		// 요청
    		fetchPost('/loginAction', obj, loginCheck)
    		})
    		
    		SignUpId.addEventListener('blur', function(){
    			
    			//입력체크
    			if(!SignUpId.value){
    				signupMsg.innerHTML = "아이디를 입력 해주세요";
    				return;
    			}
    			
    			//파라미터 세팅
    			let obj = { id : SignUpId.value };
    			console.log("아이디 체크", obj);
    			
    			//아이디 체크 > 서버에 다녀와야해요
    									// 아이디 체크에 대한 콜백함수도 만들어야겠죠~	
    			fetchPost('/idCheck', obj,  (map) => {
    		    	   if(map.result == 'success'){
    		       		   //아이디 사용 가능
    		    		   idCheckRes.value = '1';
    		       		   SignUpName.focus();
    		    	   } else {
    		    		   //아이디 사용 불가능
    		    		   idCheckRes.value = '0';	
    		    		   SignUpId.focus();
    		    		   SignUpId.value='';
    		    	   }
    		    	   signupMsg.innerHTML = map.msg; //메세지 출력
    		       });
    		});
    		
   			//비밀번호 체크 > 서버에 안가도 돼요
    		pwCheck.addEventListener('blur', function(){
    			
    			 if(!SignUpPw.value){
    				 signupMsg.innerHTML = '비밀번호를 입력해주세요';
    				 return;
    			 }
    			 if(!pwCheck.value){
    				 signupMsg.innerHTML = '비밀번호 확인을 입력해주세요';    				 
    				 return;
    			 }
    			 if(SignUpPw.value == pwCheck.value){
    				 pwCheckRes.value=1;
    				 signupMsg.innerHTML='';
    			 } else {
    				 signupMsg.innerHTML = '비밀번호가 일치하지 않습니다';    				 
    				 pwCheckRes.value=0;
    				 SignUpPw.focus();
    				 pwCheck.value='';
    				 SignUpPw.value='';
    			 }
    		});
   			
    		btnSignup.addEventListener('click', function(e){
    			
    			//이벤트 초기화
    			e.preventDefault();
    			
    			let id = SignUpId.value;
    			let pw = SignUpPw.value;
    			let name = SignUpName.value;
    			
    			if(!id){
    				signupMsg.innerHTML = '아이디를 입력해주세요';
    				return;
    			}
    			if(!pw){
    				signupMsg.innerHTML = '비밀번호를 입력해주세요';
    				return;
    			}
    			if(!name){
    				signupMsg.innerHTML = '이름를 입력해주세요';
    				return;
    			}
    			
    			//아이디 중복체크 확인
    			if(idCheckRes.value != 1){
    				signupMsg.innerHTML = '아이디 중복체크를 해주세요';
    				SignUpId.focus();
    				return;
    			}
       			//비밀번호 중복체크 확인
    			if(pwCheckRes.value != 1){
    				signupMsg.innerHTML = '비밀번호가 일치하는지 확인해주세요!';
    				pwCheck.focus();
    				return;
    			}
    			
    			
    			obj = {
    					id : id
    					, pw : pw
    					, name : name
    			}
    			
    			console.log('회원가입 obj : ', obj);
    			
    			//회원가입 요청
    			fetchPost('/register'
    						, obj
    						, (map)=>{
    							if(map.result == 'success'){
    								location.href='/login?msg='+map.msg;
    							} else {
    								signupMsg.innerHTML = map.msg;
    							}
    			});
    		})
  	})       
    	
    	
      	function loginCheck(map){
		//로그인성공 > list로 이동
		//로그인 실패 > 메세지 처리
		if(map.result == 'success'){
			location.href=map.url;
		} else {
			msg.innerHTML=map.msg;
		}		
		console.log(map);
      }

   </script>   	
   <script  type="text/javascript" src="/resources/js/common.js"> </script>

  </head>
  <body class="text-center">
 
	<main class="form-signin w-100 m-auto">
	<!--  페이지가 바뀐것은 폼이 서브밋 되었다는 것! -->
	  <!-- 로그인 폼 -->
	  <form name='signinForm'>
		    <img class="mb-4" src="/resources/css/bootstrap-logo.svg" alt="" width="72" height="57">
		    <h1 class="h3 mb-3 fw-normal">로그인</h1>
		    <div id='msg'>${param.msg }</div>
		
		    <div class="form-floating">  <!--★ required="required" 입력되었을 때만 로그인  -->
		      <input type="text" class="form-control start" required="required" id="id" placeholder="id">
		      <label for="id">Id</label>
		    </div>
		    <div class="form-floating">		 <!--★ required="required" 입력되었을 때만 로그인  -->
		      <input type="password" class="form-control end" required="required" id="pw" placeholder="Password">
		      <label for="pw">Password</label>
		    </div>
		
		    <div class="checkbox mb-3">
		      <label>
		        <input type="checkbox" value="remember-me"> Remember me
		      </label>
		    </div>
		    
		    <button class="w-100 btn btn-lg btn-primary" id="btnLogin" type="submit">로그인</button>  <!-- type="submit" -->
		    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>
	  </form>
	  
	  <!-- 회원가입폼 -->
	  <form name='signupForm' style='display:none'>
		
		    <img class="mb-4" src="/resources/css/bootstrap-logo.svg" alt="" width="72" height="57">
		    <h1 class="h3 mb-3 fw-normal">회원가입</h1>
			<div id="signupMsg"></div>
		
		    <div class="form-floating">
		      <input type="text" class="form-control start" id="SignUpId" placeholder="id">
		      <label for="SignUpId">id</label>
		    </div>
   		   	  <div class="form-floating">
		      <input type="text" class="form-control middle" id="SignUpName" placeholder="Password">
		      <label for="SignUpName">name</label>
		    </div>
		    <div class="form-floating">
		      <input type="password" class="form-control middle" id="SignUpPw" placeholder="Password">
		      <label for="SignUpPw">Password</label>
		    </div>
		    <div class="form-floating">
		      <input type="password" class="form-control end" id="pwCheck" placeholder="PasswordCheck">
		      <label for="pwCheck">PasswordCheck</label>
		    </div>
		    
		    <button class="w-100 btn btn-lg btn-primary" id="btnSignup" type="submit">회원가입</button>
		    <p class="mt-5 mb-3 text-muted">&copy; 2017–2022</p>

		<input type="text" value="0" id="idCheckRes"><br>  <!-- 1이 되어야 체크한것 -->
		<input type="text" value="0" id="pwCheckRes"><br>
	  </form>

	  <button id='btnSignupView' >회원가입</button>
	  <button id='btnSigninView'>로그인</button>
	</main>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
    
  </body>
</html>

