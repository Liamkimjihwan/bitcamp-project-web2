$('#submit-btn').click(function() {
	// 로그인 버튼을 눌렀을 때 #save-email 이 태그의 체크 상태에 따라서
	// 이메일 저장이 체크되어있으면 쿠키에 저장하고
	// 체크 되어 있지 않으면 쿠키에서 제거한다.
	 if ($('#save-email').is(':checked')) { // this = 이벤트가 발생된 객체
		    setCookie('email', $('#email').val(), 30); // 30일 동안
		  } else {
			  setCookie('email', '', 0);
		    //setCookie('email', '', 0, '/bitcamp-project-web2/auth');
		  }
		
	var param = {
		email:$('#email').val(),
		password:$('#password').val(),
		userType:$('input[name=user-type]:checked').val()
		//saveEmail:document.querySelector('#save-email').checked 
		// input 태그에서 name 이 user-type 인걸 모두 찾아라.  == CSS 기술 중에서 selector 라는 문법
	    //그중에서 checked 된 것만(학생 또는 매니저 또는 강사)상태인것만 찾아라.
		// 선택된것 중의 값만 꺼낸다.
	    // id 형식은 # , class 는 . , name
	};
	//var userTypeList = ; // css 에서는 클래스로 취급함 . 에 대해서.  
	 //console.log(param); param 값이 제대로 넘어왔는지 확인하기 위한 디버깅
	 
	$.post('login.json', param, function(ajaxResult) {
		if (ajaxResult.status == "success") {
			location.href = "../student/main.html";
			return;
		}
		alert(ajaxResult.data);
		//console.log(ajaxResult); // 서버로부터 받은 값을 출력해보자.
		
	}, 'json'); // 서버에서 보내는 데이터의 상태ㅐ가 json이라는 것을 알려줌.

}); // click
// email 쿠키가 있다면 값을 넣는다.
$('#email').val(getCookie('email').replace(/"/gi,'')); // email을 받으면 "user01@test.com"으로 나오기 때문에 "" 없앰. 
// #email라는 인풋박스를 찾아 값을 넣겠다.
// 리스너 document.querySelector('#save-email').onclick = function(event) { // 만약 클릭하게 되면 함수 호출

