<%@ page language="java" 
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    trimDirectiveWhitespaces="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<form action='update.do' method='POST' enctype='multipart/form-data'>
<div style='margin:auto;'>
<div style='border:1px; padding-top:50px; padding-bottom:10px; text-align:center; margin:auto;'>
<h2 style='border-bottom:3px solid gray; width:500px; margin:auto;'>${board.title}</h2>
</div>
</div>

<table border='1' style='margin:auto;'>
<tr><th><input name='contentNo' type='text' value='${board.contentNo}' readOnly style='border:none; text-align:center;'></th><th>
<input name='name' type='text' value='${board.name}' readOnly style='border:none; text-align:center;'></th><th>
<input type='text' value='                         ' readOnly style='border:none; text-align:center;'><th>
<input name='registerDate' value='${board.registerDate}' readOnly style='border:none; text-align:center;'></th><th>
<input name='viewCount' value='${board.viewCount}' readOnly style='border:none;'></th></tr><tr><td colspan='5'><br>
<textarea name='contents' rows='30' cols='125' style='text-indent:20px; border:none;'>${board.contents}</textarea><br></td></tr>
</table>

<div style='width:1000px; margin:auto;'>
<input type='hidden' name='title' value='${board.title}'>
<input type='hidden' name='contentNo' value='${board.contentNo}'>
<div style='width:300px; height:60px; text-align:center; padding-top:10px;'>
<button type='submit'>수정</button> 
<a href='delete.do?contentNo=${board.contentNo}'>삭제</a>
<a href='list.do'>목록</a>
</div>
</div>
</form> 
