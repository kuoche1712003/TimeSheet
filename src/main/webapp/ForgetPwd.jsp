<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
  
<!DOCTYPE html>
<html>
<head>
     <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>忘記密碼</title>
    <!-- Bootstrap -->
  <link href="css/bootstrap.css" rel="stylesheet">
  <link href="css/style.css" rel="stylesheet"> 

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5  elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<%
	
	 if(request.getParameter("forget_status")!=null){
		int forget_status = Integer.parseInt(request.getParameter("forget_status")); 
		String Email = request.getParameter("Email");
		if(forget_status == 7){	
			
%>
<script>
	alert("已傳送新密碼至 <%=Email %>，\n\n請重新登入並修改密碼‧");
</script>
<%
		}else if (forget_status == 6){
%>

<script>
	alert("系統設定郵件時失敗，請重新嘗試");
</script>
<%
		}else if(forget_status == 8){
%>
<script>
	alert("更新密碼時失敗，請重新嘗試");
</script>
<%
		}else if (forget_status == 2){
			
%>
<script>
alert("該員工編號無電子郵件，請聯絡系統管理員");
</script>

<% 
	}else if(forget_status == 3){
%>
<script>
	alert("無此員工編號");
</script>
<% }else{

%>
<script>
	alert("系統發生錯誤");
</script>
<%
}}
%>

<body class="loginPage ForgetPwdPage">

<div class="container">
  <div class="box"> <!--網站主內容-->
  <a href="index.jsp" class="btn-back rightFloat">返回 ></a>
  <div class="forPwdTitle">忘記密碼</div><br>
  <form class="form-horizontal" action="ForgetPwdController" method="post">

    <c:if test="${not empty Msgs['status']}">
       	<div class="col-sm-offset-2 col-sm-8 error_msg" id="error_msg">
       		${Msgs['status']}
       	</div>
  	</c:if>
  	<c:if test="${not empty errorMsgs['errorMsgs']}">
  		<div class="col-sm-offset-2 col-sm-8 error_msg" id="error_msg">
  			${errorMsgs['errorMsgs']}
  		</div>
  	</c:if>
    <c:if test="${not empty errorMsgs['error']}">
    	<div class="col-sm-offset-2 col-sm-8 error_msg" id="error_msg">
  			${errorMsgs['error']}
  		</div>
  	</c:if>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-8">
      <input type="text" class="form-control pwd-form" id="emid" name="emid" placeholder="請輸入員工編號" required>
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-12">
          
    </div>
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-8">
      <button type="submit" class="btn btn-login2 btn-lg btn-block">寄送電子郵件</button>
    </div>
  </div>
  
</form>
</div>

</div>


  <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

</body>
</html>
