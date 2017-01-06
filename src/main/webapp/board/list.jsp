<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
 <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 


<h1>게시판 정보</h1>
<c:if test="${member == null}">
<a href='../auth/loginform.do'>글쓰기</a><br></c:if>
<c:if test="${member != null}">
<a class='btn btn-default' href='form.jsp' role='button'>글쓰기</a><br></c:if>
<table border='1' class='table table-striped'>
<tr>
  <th>번호</th>
  <th>제목</th>
  <th>작성자</th>
  <th>등록일</th>
  <th>조회수</th>
</tr>
<c:forEach var="board" items="${board}">
<tr> 
  <td>${board.contentNo}</td>
  <td><a href='detail.do?contentNo=${board.contentNo}'>${board.title}</a></td>
  <td>${board.name}</td>
  <td>${board.registerDate}</td>
  <td>${board.viewCount}</td>
</tr>
</c:forEach>
</table>
