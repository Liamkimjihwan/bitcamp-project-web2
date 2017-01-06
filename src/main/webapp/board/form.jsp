<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../header.jsp" ></jsp:include>

<h1>게시판 글쓰기</h1>
<form action='add.do' method='POST' enctype='multipart/form-data'>
<table border='1'>
<tr><th>제목</th><td><input name='title' type='text' size= '100' placeholder='예)제목을 입력해주세요'></td></tr>
<tr><th>내용</th><td><textarea name='contents' rows='50' cols='125' placeholder='예)내용을 입력해주세요'></textarea></td></tr>
<tr><th>첨부파일</th><td><input name='filePath' type='file'></td></tr>
</table>
<button type='submit'>등록</button>
  <a href='list.do'>취소</a>
</form>

<jsp:include page="../footer.jsp" ></jsp:include>
