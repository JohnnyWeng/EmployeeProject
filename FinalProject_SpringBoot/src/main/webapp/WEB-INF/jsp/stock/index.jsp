<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spform" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
<!-- rel: The required rel attribute specifies the relationship between the current document and the linked document/resource.-->
	<link rel="stylesheet" 
		href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
	<meta charset="UTF-8">
	<title>Stock Form</title>
<!-- CSS寫法: 錯誤訊息 -->
<!-- .css 的 . 就是 class 的意思 -->
	<style type="text/css">
		.error { 				
			color: #FF0000;
		}
	</style>
</head>
<body style="padding: 20px">
	<spform:form class="pure-form" 
				 method="post" 
				 modelAttribute="stock" 
				 action="${pageContext.request.contextPath}/mvc/stock/">
		<fieldset>
			<legend>Stock Form</legend>
			股號：<spform:input path="symbol" />
				 <spform:errors path="symbol" cssClass="error" /> <!-- 使用上面的 error 就不用 . 了 -->
				 <p /> 
			價格：<spform:input path="price" /> 
				 <spform:errors path="price" cssClass="error" />
				 <p />
			數量：<spform:input path="amount" />
				 <spform:errors path="amount" cssClass="error" />
				 <p />
			<input type="submit" value="新增" 
				class="pure-button pure-button-primary"/>
			<p />
			<spform:errors path="*" cssClass="error" /> <!-- 所有錯誤都在下面 -->
		</fieldset>
	</spform:form>
	<hr />
	${ stocks }
</body>
</html>