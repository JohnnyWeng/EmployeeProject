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
<title>User List</title>
</head>
<body style="padding: 20px"> <!--  整個離頁框 20px 這樣觀感會好很多 -->
	<table class="pure-table pure-table-bordered">
		<thead>
			<tr><th>Name</th><th>Age</th><th>Delete</th><th>Update</th></tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${ users }">  <!-- EL Language --> <!-- 把 users 撈出來, users 變數從 UserController 的 read 丟進來的 users -->
			<tr>
				<td>${ user.name }</td> <!--  user 是從上面 var 來的 -->
				<td>${ user.age }</td>
				<!--  把 delete 功能寫在這邊,因為 forEach 所以把資料撈出之後分別給 delete 和 update 功能 -->
				<td><input type="button" value="Delete" onclick="window.location.href='./delete/${ user.name }';" class="pure-button pure-button-primary"/></td> <!--  EL Language --> 
				<td><input type="button" value="Update" onclick="window.location.href='./get/${ user.name }';" class="pure-button pure-button-primary"/></td>
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<p/> <!-- 這是為了做間隔(back 跟上面 table 有些間隔) -->
	<input type="button" value="back" onclick="window.location.href='./input';"> <!--  這是做 "back 回車鍵" -->
</body>
</html>







