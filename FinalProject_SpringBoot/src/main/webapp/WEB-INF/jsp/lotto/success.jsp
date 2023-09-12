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
<title>Lotto Success</title>
</head>
<body style="padding: 20px"> <!--  整個離頁框 20px 這樣觀感會好很多 -->
	<!-- <form method="post" action= 指定 Form 送出後的目的地(相對路徑)-->
	<!-- 這裡的 ${ action } 是一個變數, 我們要有一個 action 才知道現在是 add 還是 update 狀態, 用 action 來決定 -->
	<!-- Form 有 get 回到主畫面: ${pageContext.request.contextPath}/mvc/lotto/ -->
		<!--  使用 get 引導回首業, 當你按下方 "回首頁" 就回首頁了-->
	<form class="pure-form", method="get" action="${pageContext.request.contextPath}/mvc/lotto/">  
		<fieldset>
			<legend>Lotto Success</legend>
			<!-- 下面必須有這行 ${ param.lotto } 才能做顯示 -->
			${ param.lotto }  <!-- controller 使用 attr.addAttribute("lotto", lotto) -->
			
			<!--  樂透成功, 顯示樂透, jsp 是去撈 servlet, 所以使用 EL(前端 JSP 才可以使用) -->
			${ lotto } <!--  controller 使用 addFlashAttribute("lotto", lotto); -->
			<p />
			<input type="submit" value="回首頁"  class="pure-button pure-button-primary" />  
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
 

 
 
 
 
 
 
 