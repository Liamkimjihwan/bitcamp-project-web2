$(function() { // 페이지가 다 로딩된 후 호출.
	// header.html을 가져와서 붙인다.
	$.get('../header.html', function(result) { // 먼저 html 가져오고 나서 아래 상태들 호출.
		// 헤더.html 을 가져와서 데이터는 result에 담겨있음. 
		// 서버에서 로그인 정보를 가져온 다음에 감출건 감추고 #header에 담는다. 
		// 서버에서 로그인 사용자 정보를 가져온다.
		$.getJSON('../auth/loginUser.json', function(ajaxResult) {
			$('#header').html(result); // header.html에 넣으면

			if (ajaxResult.status == "fail") { // 로그인 되지 않았으면, 
				// 로그온 상태 출력 창을 감춘다.
				$('#logon-div').css('display', 'none');
				// 로그인 버튼의 클릭 이벤트 핸들러 등록하기
				$('#login-btn').click(function(event) {
					event.preventDefault();
				     location.href = '../auth/main.html';					
				});
				return;
			}
			// 로그인 되었으면, 로그오프 상태 출력 창을 감춘다.
			$('#logoff-div').css('display', 'none');	
			$('#logon-div img').attr('src', '../upload/' + ajaxResult.data.photoPath); // src에 넣겠다.
			$('#logon-div span').text(ajaxResult.data.name);
			
			// 로그아웃 버튼의 클릭 이벤트 핸들러 등록하기
			$('#logout-btn').click(function(event) { // #logout-btn 태그를 클릭했을 때 함수를 function(event)호출한다.
				event.preventDefault();
				$.getJSON('../auth/logout.json', function(ajaxResult) {
			//        서버에 요청해서 서버에서 응답이 오면 등록한 function(jsonText)를 호출해라.
					location.href = '../auth/main.html';
					// location.href = 현재 탭에서  ../auth/main.html 를 요청해 뿌려라. 
				});
			});
		});
	});
	// sidebar.html을 가져와서 붙인다.
	$.get('../sidebar.html', function(result) {
		$('#sidebar').html(result);
	});

	// footer.html을 가져와서 붙인다.
	$.get('../footer.html', function(result) {
		$('#footer').html(result);
	});
});