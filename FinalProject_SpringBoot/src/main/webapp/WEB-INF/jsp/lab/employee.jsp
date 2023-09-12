<%@ page isErrorPage="true"%> <!-- jsp 只接受 GET/POST 不接受 PUT, 所以可以加上這行 -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="spform" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
	<link rel="stylesheet"
		href="https://unpkg.com/purecss@2.0.6/build/pure-min.css">
	<meta charset="UTF-8">
	<title>Employee Form</title>
<!-- CSS寫法: 錯誤訊息 -->
<!-- .css 的 . 就是 class 的意思 -->
	<style type="text/css">
.error {
	color: #FF0000;
}

legend {
	background-color: #B8EBFF;
	color: white;
	padding: 5px 10px;
}
</style>
	
<!-- The HTML <script> tag is used to define a client-side script (JavaScript). Common uses for JavaScript are image manipulation, form validation, and dynamic changes of content.
	 To select an HTML element, JavaScript most often uses the document.getElementById() method. 也就是說可以在 html 安插 javascript  -->
	 <!-- .value 是取值的意思, 也就是取得 input 欄位的 value, 這個 _method 是一個 id, 從 employe_form.jspf 來的 -->
	 <!-- employee_form.jspf 會把 'DELETE' 傳進來, 改變方法 -->
	<script type="text/javascript"> 
		function changeMethod(methodValue) { <!-- changeMethod 是給 employee_form.jspf 的 delete method 用的 -->
			document.getElementById("_method").value = methodValue;
			document.getElementById('employee').submit();
		}
	</script>
	
	<!-- Google Chart -->
	<!-- haad區 -->
	<script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
	<script type="text/javascript">
	<!-- 載入Visualization API，括號裡第一個參數是版本名稱或數字-->
      google.charts.load('current', {'packages':['corechart']}); // 圖表類型, Column Chart 和 Pie Chart 都是 corechart(核心 Chart)
      google.charts.setOnLoadCallback(drawChart); // google.charts.setOnLoadCallback 會呼叫下面 drawChart 的 function
      
      // 我們資料只有計算 salary 和 job 兩個的
	  function drawChart() { // 呼叫下面兩個 function
		  salaryChart();
		  jobsChart();
	  }
      
	// salaryChart -> 長條圖, 圓餅圖 兩個一起定義在這個 function
      function salaryChart() {
        var data = google.visualization.arrayToDataTable([
          ['ename', 'salary'], // 定義下面標題
          <!-- 這裡是前端的 js 所以不會提示, 要小心寫, 我們這裡是 jstl 產生 js 的程式語法 --><!-- 組合 -->
          <!-- 我們這邊是利用 EL 把 employees 值拿到, 再用 for-each 一個個撈出, 然後 [ ] 顯示 -->
          <c:forEach var="emp" items="${ employees }">  
         	 ['${ emp.ename }', ${ emp.salary }], 
		  </c:forEach>
        ]);
        
        var options = {
          title: 'Salary' <!-- 標提 -->
        };
        // 長條圖(縱)
         //建立object，以使用.draw方法畫出帶有data, options資料的圖表
    	//若要換成繪製圓餅圖改用google.visualization.PieChart，但data數據需要再調整
        var columnChart = new google.visualization.ColumnChart(document.getElementById('column_chart')); <!-- javascript 去抓下面 body 內 table 的 -->
        columnChart.draw(data, options);
        // 圓餅圖
        var pieChart = new google.visualization.PieChart(document.getElementById('pie_chart'));
        pieChart.draw(data, options);
      }
      
   // jobsChart
      function jobsChart() { // 寫 job 的
          var data = google.visualization.arrayToDataTable([
              ['ename', 'salary'],
              <!-- 這裡是前端的 js 所以不會提示, 要小心寫, 我們這裡是 jstl 產生 js 的程式語法 -->
              	<c:forEach var="emp" items="${ employees }">  <!-- 組合 -->
              	// copy 老師的
	        	// 判斷工作數量是否  > 0, 判斷 job 陣列是否大於 0 (是否有資料)
	        	<c:if test="${ fn:length(emp.jobs) > 0 }">
	        		// 判斷第一筆工作的 jid 不可以是 null(因為 left join 有可能會是 null)
			        <c:if test="${ emp.jobs[0].jid != null }"> 
			        	['${ emp.ename }', ${ fn:length(emp.jobs) }], // fn: 是 el function(), 上面的 < %@ taglib prefix="fn" uri 可以計算長度
			        </c:if>
			        <c:if test="${ emp.jobs[0].jid == null }"> // 假如第一筆 jid 是 null, 我給它 0, 這樣才不會出錯, 也就是說以後可能會是 null 的都直接讓它變成 0
			        	['${ emp.ename }', 0],
			        </c:if>	
		        </c:if>
    		  </c:forEach>
            ]);

            var options = {
              title: 'Salary'
            };
            // 折線圖
            var linechart = new google.visualization.LineChart(document.getElementById('line_chart'));
            linechart.draw(data, options);
    	  
      }
</script>	
</head>

<!-- body區 -->
<body style="padding: 20px">
	<!--  設計表格: 這裡才是真正定義頁面的 -->
	<table>
		<tr>
			<!-- 放 Employee Form 的地方 -->
			<td valign="top">
			<!-- 因為把 code 移到了 jspf, 所以要在下面加上這行 -->
			<!-- Review, JavaEE_9~11(p.66): < jsp :include > : 這個標籤的目的是動態的載入其他的 jsp 片段, 那你的這個 "jsp 片段"可以像模組一樣用拼的-->
				<%@include file="employee_form.jspf" %> 
			</td>
			<!-- Salary Column Chart -->
			<td valign="top">
				<form class="pure-form">
				<!-- The <fieldset> tag is used to group related elements in a form.
				The <fieldset> tag draws a box around the related elements. -->
					<fieldset>
						<legend>Salary Column Chart</legend>
						<!-- 定義 google chart 的 body -->
						<div id="column_chart" style="width: 400px; height: 250px"></div>
					</fieldset>
				</form>
			</td>
			<!-- Salary Pie Chart -->
			<td valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>Salary Pie Chart</legend>
						<div id="pie_chart" style="width: 400px; height: 250px"></div>
					</fieldset>
				</form>
			</td>
			<!-- Jobs Line Chart -->
				<td valign="top">
				<form class="pure-form">
					<fieldset>
						<legend>Jobs Line Chart</legend>
						<div id="line_chart" style="width: 400px; height: 250px"></div>
					</fieldset>
				</form>
			</td>
		</tr>
		<tr>
			<!-- Employee List --> <!-- 小心 $ { ... } 的 $ 和 {} 是不空格的 -->
			<td valign="top" colspan="4">
			<!-- ${ employees } 改成 "employee_list.jspf" -->
			<%@include file="employee_list.jspf" %> 
			</td>
		</tr> 
	</table>

</body>
</html>