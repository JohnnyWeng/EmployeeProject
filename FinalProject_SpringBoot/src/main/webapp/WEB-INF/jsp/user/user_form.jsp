<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!--  https://www.runoob.com/jsp/jsp-jstl.html 直接 copy 過來 -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <!--  核心标签 -->

<%@ taglib prefix="fmt" 
           uri="http://java.sun.com/jsp/jstl/fmt" %>   <!--  格式化标签 -->
           
<%@ taglib prefix="fn" 
       uri="http://java.sun.com/jsp/jstl/functions" %>   <!--  JSTL函数 -->

<!-- 要了解這個 user_form.jsp 要真正運行網頁看 -->

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
<title>User Form</title>
</head>
<body style="padding: 20px"> <!--  整個離頁框 20px 這樣觀感會好很多 -->
	<!-- <form method="post" action= 指定 Form 送出後的目的地(相對路徑)-->
	<!-- 這裡的 ${ action } 是一個變數, 我們要有一個 action 才知道現在是 add 還是 update 狀態, 用 action 來決定 
	我們有兩種 action, 這是因為 update 和 list 使用的頁面 "都是這個 form", 所以我們畫面上最左邊的按件, 要分 update 或 add, 另外兩個 reset 和 read 則不變 -->
	<form class="pure-form" method="post" action="/Springmvc/mvc/user/${ action }">  
		<fieldset>
			<legend>User Form</legend>
			<!-- placeholder: 在輸入框的顯示提示字-->
			<!-- user.name 和 user.age 是給 update page(也是這個 page) 用的, 作為預設值 -->
			User name: <input type="text" id="name" name="name" value="${ user.name }" ${ readonly } placeholder="Please input name"/>
			<p /> <!-- 這是為了做間隔 -->
			User age: <input type="number" id="age" name="age" value="${ user.age }" placeholder="Please input age"/>
			<p />
			<input type="submit" value="${ action }"  class="pure-button pure-button-primary" />
			<input type="reset" value="reset"  class="pure-button pure-button-primary" />
			<!--  這裡的網址是 .../mvc/user/input 要傳到 .../mvc/user/read (所以要先回到 user 再到 read)--> 
			<!-- 下面 read 的 url 要給全名 -->
			<input type="button" value="read" onclick="window.location.href='/Springmvc/mvc/user/read';" class="pure-button pure-button-primary" />  
		</fieldset>
	</form>
</body>
</html>

<!--  
<fieldset>:

The <fieldset> tag is used to group related elements in a form.

The <fieldset> tag draws a box around the related elements.

The <legend> tag defines a caption for the <fieldset> element.
 -->
 
 <!--  這個 form 的 URL 是 http://localhost:8080/Springmvc/mvc/user/input --> 
 
 
 
 
 
 
 
 