//학생 목록 가져와서 tr 태그를 만들어 붙인다.
$.getJSON('list.json', function(ajaxResult) { // 파싱되어진 자바스크립트 객체가 넘어오기 때문에 파싱은 따로 필요 없음.
  /* @RestController 페이지 컨트롤러의 메서드가 리턴한 값은 
   * AjaxResult의 프로퍼티 값으로 JSON 문자열을 만든다.
   * 따라서 status나 data 프로퍼티를 바로 꺼내면 된다.
   */
  var status = ajaxResult.status;
  
  if (status != "success")
    return;
  
  var list = ajaxResult.data;
  var tbody = $('#list-table > tbody'); // 태그 찾는..bit는 무조건 여러개를 리턴함. $는 bit.js에 있는 함수
  
  for (var student of list) {
    $("<tr>") // $("<tr>") 리턴값에 대해서 html을 호출.tr 태그 만들고 html은 아래있는것들로 만들어라.
            // 그리고 tbody에 붙여라.
    // <a class='name-link' 는 a태그를 
    // html 은 시작 태그와 끝 태그 사이에 innerHTML을 해서 넣는다.
    // 태그 개수가 여러개면 알아서 여러번 값을 반복해서 넣는다.
    
    .html("<td>" +  
	      student.memberNo + "</td><td><a class='name-link' href='#' data-no='" + 
	      student.memberNo + "'>" + 
	      student.name + "</a></td><td>" + 
	      student.tel + "</td><td>" + 
	      student.working + "</td><td>" +
	      student.grade + "</td><td>" +
	      student.schoolName + "</td>")
	.appendTo(tbody); // 
  }
  
  // 학생 목록에서 이름 링크에 click 이벤트를 처리한다.
  //var al = $('.name-link'); // document.querySelectorAll는 태그르르 찾는 문법.
  $('.name-link').click(function(event) { // 클릭을 했을 때 이 함수를 실행하도록.
	  // name-link 라는 라벨이 여러개이면 반복문을 돌려서 
	  // name link라는 클래스(소속)인 라벨들
	  event.preventDefault();
      //location.href = 'view.html?memberNo=' + this.getAttribute("data-no");
	  location.href = 'view.html?memberNo=' + $(this).attr("data-no");
      // this.attr("data-no") jquery 함수로 하면 안되는게 this는 오리지널 tags를 말함.
  });
});

// 추가 버튼에 클릭 이벤트 핸들러(리스너) 등록하기
$('#new-btn').click(function(event) {// 클릭 이벤트가 발생했을 때 호출될 함수.
	event.preventDefault(); // a 태그는 기본적으로 href로 설정된 url을 호출하는데 
	                        // 넘어가는 행위는 막는게 좋다.
	// 다음과 같이 javascript 명령으로 화면을 이동하면, ]
	// 캐시된 파일이 로딩되지 않고 정상적으로 자바스크립트를 실행한다.
  location.href = 'view.html'; // view.html로 이동하라.
});
