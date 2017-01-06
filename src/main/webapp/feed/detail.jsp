<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>

<h1>상세 정보</h1>
<form action='update.do' method='POST'>
<table border='1'>
<tr><th>게시물 번호</th><td><input name='cono' type='text' value='${feed.contentNo}' readonly></td></tr>
<tr><th>조회수</th><td><input name='vw_cnt' type='text' value='${feed.viewCount}' readonly></td></tr>
<tr><th>작성자 이름</th><td><input name='name' type='text' value='${feed.name}' readonly></td></tr>
<tr><th>등록일</th><td><input name='rdt' type='text' value='${feed.registerDate}'></td></tr>
<tr><th>내용</th><td><input name='conts' type='text' value='${feed.contents}'></td></tr>
</table>
<button type='submit'>변경</button>
 <a href='delete.do?contentNo=${feed.contentNo}'>삭제</a>
 <a href='list.do'>목록</a>
</form>
