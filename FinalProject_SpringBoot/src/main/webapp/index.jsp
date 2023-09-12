
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>HomePage</title>
</head>
<body>
	<ol>
	<!-- 也可以全部改成"絕對路徑": ${pageContext.request.contextPath} 全部取代 Springmvc -->
	<!-- <li><a href="${pageContext.request.contextPath}/mvc/user/input">User Form</a></li>  -->
	<!-- 下面兩種都可以  -->
	<!-- 下面是用 restful 方式去 mapping 到 controller 的方法 (EmpController...)  -->
		<li><a href="mvc/user/input">User Form</a></li>
		<li><a href="${pageContext.request.contextPath}/mvc/lotto/">Lotto Form</a></li>
		<li><a href="${pageContext.request.contextPath}/mvc/employee/">Employee Form</a></li>
		<li><a href="${pageContext.request.contextPath}/mvc/person/">Person Form</a></li>
		<li><a href="${pageContext.request.contextPath}/mvc/stock/">Stock Form</a></li>
		<li><a href="${pageContext.request.contextPath}/mvc/lab/emp/">Lab: Employee ＆ Job Form</a></li>
		<li><a href="${pageContext.request.contextPath}/mvc/lab/job/">Lab: Employee ＆ Job Form</a></li>
	</ol>
</body>
</html>

<!-- index.jsp 不放在 WEB-INF, 但是 hello.jsp 放在 WEB-INF, 因為 index.jsp 呼叫 hello.jsp  -->

<!-- http://localhost:8080/springmvc/index.jsp -->