<%@page import="java.util.List"%>
<%@page import="bitcamp.java89.ems2.domain.Student"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
       trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

       

<!DOCTYPE html>
<html>
<head>
<meta charset='UTF-8'>
<title>할일관리-목록</title>
</head>
<body>

<jsp:include page='../header.jsp'></jsp:include>
<h1>할일 정보</h1>
<a href='form.html'>추가</a><br>
<table border='1'>
<tr>
  <th>번호</th>
  <th>순서</th>
  <th>이름</th>
  <th>프로젝트명</th>
  <th>상태</th>
  <th>등록일</th>
</tr>

<c:forEach var="todo" items="${requestScope.todos}">
<tr> 
<td><a href='detail?contentNo=$todo.tdno'>${todo.tdno}</a></td>
<td>${todo.seq}</td>
<td>${todo.name}</td>
<td>${todo.titl}</td>
<td>${todo.stat}</td>
<td>${todo.rdt}</td>
</tr>

</c:forEach>
</table>
<jsp:include page='../footer.jsp'></jsp:include>

</body>
</html>
