<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%> <!-- 必須要有這行才能寫中文 -->
	
<!DOCTYPE html>
<html lang="zh-Hant-Tw">
 <head>
   <title>Learning JQuery</title>

   <meta charset="utf-8">
   <meta name="viewport" content="width=device-width, initial-scale=1"/>
	<script type="text/javascript"
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	
</head>
<body>

  <script type="text/javascript">
  
    $.get("test.html", function(data, status){

        alert(status);

    });
    
    //alert("Hello! I am an alert box!!");
    

/* var jqxhr = $.ajax( "test.html" )
  .done(function() { // 只有在AJAX调用成功时才会触发，即最终返回 HTTP 200状态。
    alert( "success" );
  })
  .fail(function() { // error 请求失败；
    alert( "error" );
  })
  .always(function() { // complete 请求结束，则会触发，无论成功与否。
    alert( "complete" );
  }); 
   */
  
   $(document)
   
  </script>

</body>

<!-- <script>
$(document).ready(function(){
  $("button").click(function(){
    $("#div1").load("test.html", function(responseTxt, statusTxt, xhr){
      if(statusTxt == "success")
        alert("External content loaded successfully!");
      if(statusTxt == "error")
        alert("Error: " + xhr.status + ": " + xhr.statusText);
    });
  });
});
</script>
</head>
<body>

<div id="div1"><h2>Let jQuery AJAX Change This Text</h2></div>

<button>Get External Content</button>

</body> -->

</html>




