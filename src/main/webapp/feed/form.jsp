<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
    
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:include page="../header.jsp" ></jsp:include>

<div style='color:grey; font-weight:bold; margin-left:10px; padding-top:30px; font-family:돋움체,sans-serif; font-size:large;'>Feed 입력</div>

<form action='add.do' method='POST'>
<table border='1'>
<tr><th>피드내용</th><td><input name='conts' type='text' placeholder='피드 내용 입력' size="80"></td></tr>
<tr><th>태그</th><td><input name='tagName' type='text'  placeholder='#태그 #알파 #베타' size="80" ></td></tr>

</table>
<button type='submit'>등록</button>
 <a href='list.do'>목록</a>
</form>

<jsp:include page="../footer.jsp" ></jsp:include>