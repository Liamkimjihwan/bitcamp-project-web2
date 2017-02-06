try {
  var memberNo = location.href.split('?')[1].split('=')[1];
} catch (error) {
  var memberNo = -1;
}
if (memberNo > 0) {
  prepareViewForm();
} else {
  prepareNewForm();
}

function prepareViewForm() {
  // 등록 버튼은 감춘다.
  $('.new-form').css('display', 'none');
  //학생 목록 가져와서 tr 태그를 만들어 붙인다.
  $.getJSON('detail.json?memberNo=' + memberNo, function(ajaxResult) {
    /* @RestController 페이지 컨트롤러의 메서드가 리턴한 값은 
     * AjaxResult의 프로퍼티 값으로 JSON 문자열을 만든다.
     * 따라서 status나 data 프로퍼티를 바로 꺼내면 된다.
     */
    var status = ajaxResult.status;
    
    if (status != "success") {
      alert(ajaxResult.data);
      return;
    }
    var student = ajaxResult.data;
    console.log(student);
    
    $('#email').val(student.email);
    $('#name').val(student.name);
    $('#tel').val(student.tel);
    if (student.working) {
      $('#working').attr('checked', 'checked');
    } else {
      $('#not-working').attr('checked', 'checked') // checked를 설정할 때는 attr를 사용해도 됨.
    }
    $('#grade').val(student.grade);
    $('#school-name').val(student.schoolName);
    $('#photo-img').attr('src', '../upload/' + student.photoPath);
  });
  
  // 삭제, 변경 버튼을 클릭 했을 때 호출될 함수(클릭 이벤트 핸들러) 등록
 $('#delete-btn').click(function() {
    $.getJSON('delete.json?memberNo=' + memberNo, function(ajaxResult) {
      if (ajaxResult.status != "success") {
        alert(ajaxResult.data);
        return;
      }
      location.href = 'main.html';
    }); // getJSON
  }); // click
  $('#update-btn').click(function() {
    var param = {
        "memberNo": memberNo, 
        "name": $('#name').val(),
        "tel": $('#tel').val(),
        "email": $('#email').val(),
        "password": $('#password').val(),
        "working": $('#working').is(':checked'),
        "grade": $('#grade').val(),
        "schoolName": $('#school-name').val()
        // val() 으로 set get 역할 할 수 있음.
    };
    //console.log($('working').is(':checked'));
    $.post('update.json', param, function(ajaxResult) {
        if (ajaxResult.status != "success") {
           alert(ajaxResult.data);
           return;
        }
        location.href = 'main.html';
    }, 'json');
  }); // click
} // prepareViewForm()

function prepareNewForm() {
  // 변경,삭제 버튼을 감춘다.
    $('.view-form').css('display', 'none'); // view-form 으로 되어있는 태그의 스타일을 모두 none으로 바꾼다.
    
    $('#add-btn').click(function() { // add-btn을 클릭했을 때 이 함수를 호출해라.
    	 var param = {
		        "name": $('#name').val(),
		        "tel": $('#tel').val(),
		        "email": $('#email').val(),
		        "password": $('#password').val(),
		        "working": $('#working').is(':checked'),
		        "grade": $('#grade').val(),
		        "schoolName": $('#school-name').val()
		        // val() 으로 set get 역할 할 수 있음.
		    };
	
	    $.post('add.json', param, function(ajaxResult) {
	        if (ajaxResult.status != "success") {
	          alert(ajaxResult.data);
	          return;
	        }
	        location.href = 'main.html';
	    }, 'json'); // post()
	  });// click
}

// 목록 버튼을 클릭했을 때 호출될 함수(이벤트 핸들러) 등록!
$('#list-btn').click(function() {
  location.href = 'main.html';
});
