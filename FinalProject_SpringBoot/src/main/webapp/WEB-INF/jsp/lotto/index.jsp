<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--  https://www.runoob.com/jsp/jsp-jstl.html 直接 copy 過來 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!--  核心标签 -->

<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>   <!--  格式化标签 -->
           
<%@ taglib prefix="fn" 
       uri="http://java.sun.com/jsp/jstl/functions" %>   <!--  JSTL函数 -->
           
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
<title>Lotto Form</title>
</head>
<body style="padding: 20px"> <!--  整個離頁框 20px 這樣觀感會好很多 -->
	<!-- <form method="post" action= 指定 Form 送出後的目的地(相對路徑)-->
	<!--  下面 action 是傳送到 Controller -> /add -> /addOk -->
	<form class="pure-form" method="post" action="/Springmvc/mvc/lotto/add">  
		<fieldset>
			<legend>Lotto Form</legend>
			<input type="submit" value="電腦選號"  class="pure-button pure-button-primary" />  
		</fieldset>
	</form>
	<p />
	<table class="pure-table pure-table-bordered"> 
		<thead> <!--  要用這個才能讓標題顏色變成 "灰色的" -->
			<tr>
				<th>index</th>
				<th>號碼 1</th><th>號碼 2</th><th>號碼 3</th><th>號碼 4</th><th>號碼 5</th>
				<th>換號</th><th>刪除</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach varStatus="status" var="lotto" items="${ lottos }">
			<tr>
				<td>${ status.index }</td> <!-- status 的 index -->
					<!-- 下面 forEach 是因為 lotto 都是 set 集合("集合中又集合"), 所以要用 for-each 去拆 set 集合 -->
				 	<c:forEach var="num" items="${ lotto }">
				 		<td>${ num }</td>
				 	</c:forEach>
				 <td>換號</td><td>刪除</td>
			<tr>
			</c:forEach>
		</tbody>
	</table>
</body>
</html>

<!--  
<fieldset>:

The <fieldset> tag is used to group related elements in a form.

The <fieldset> tag draws a box around the related elements.

The <legend> tag defines a caption for the <fieldset> element.
 -->
 
 
 
 
 
 
 
 
 
 