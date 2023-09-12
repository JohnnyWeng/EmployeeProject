<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spform" uri="http://www.springframework.org/tags/form" %> <!--导入springMvc的标签库-->
<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet"
		href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
	<meta charset="UTF-8">
	<title>Person Form</title>
<!-- CSS寫法: 錯誤訊息 -->
<!-- .css 的 . 就是 class 的意思 -->
	<style type="text/css">
		.error { 				
			color: #FF0000;
		}
	</style>
</head>
<body style="padding: 15px">
	<spform:form class="pure-form" 
				 method="post" 
				 modelAttribute="person" 
				 action="${pageContext.request.contextPath}/mvc/person/">
		<fieldset>
			<legend>Person form</legend>
			姓名：<spform:input path="name" />
				 <spform:errors path="name" cssClass="error" /> <!-- 使用上面的 error 就不用 . 了 -->
				 <p /> 
			年齡：<spform:input path="age" />
				 <spform:errors path="age" cssClass="error" />
				 <p />
			會員：<spform:radiobutton path="member" value="true" />會員
				 <spform:radiobutton path="member" value="false" />非會員
				 <spform:errors path="member" cssClass="error" />
				 <p />
			生日：<spform:input path="birth" type="date" />
				 <spform:errors path="birth" cssClass="error" />
				 <p />
			<button type="submit" class="pure-button pure-button-primary">新增</button>
			<p />
			<spform:errors path="*" cssClass="error" /> <!-- 所有錯誤都在下面 -->
		</fieldset>
	</spform:form>
	<hr />
	${ people } <!-- EL: people 資料庫(model 傳過來的)記錄所有正確加入進去的資料 -->
</body>
</html>