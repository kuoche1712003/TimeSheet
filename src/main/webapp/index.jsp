<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>

	
  
  <%@ include file="/WEB-INF/Views/Template/header.file" %>
  <title>工時系統</title>
  
</head>
<%
	
	 if(request.getParameter("status")!=null){
		int login_status = Integer.parseInt(request.getParameter("status")); 
		if(login_status == 7){	


%>
<script>
	alert("您已成功登出"," ","error");
</script>
<% 
	}
if (request.getParameter("Logout") != null) {
			session.invalidate();
	%>
	<script>
	alert("您已成功登出"," ","error");
	</script>
	<%
		}

if(request.getParameter("editpwd_status")!=null){
	 int editpwd_status =Integer.parseInt(request.getParameter("editpwd_status"));
	 if(editpwd_status == 1){ 
		 session.invalidate();
	 
	%>
<script>
	alert("您已成功修改密碼，請重新登入");
</script>
<%
	 }}}
%>
	
<body class="loginPage">

<div class="container">
<div class="box">
 <!-- 人物 -->
 <div class='circle'>
  <img src="https://dqcgrsy5v35b9.cloudfront.net/cruiseplanner/assets/img/icons/login-w-icon.png" width="120" height="120">
 </div>
 <!--網站主內容-->
  <div class="loginTitle">工時系統</div><br>
  <c:if test="${not empty Msgs['success']}">
  	<div class="col-md-3 error_msg" id="error_msg">
  		${Msgs['success']}
  	</div>
  </c:if>
  
 <form class="form-horizontal" action="LoginController" method="post">
   <!-- 登入錯誤 -->
   <c:if test="${not empty loginMsgs['error']}">
   	<div class="col-sm-offset-2 col-sm-8 error_msg2" id="error_msg">
		${loginMsgs['error']}
    </div>
   </c:if>
  <!-- 帳號為空 -->
  <c:if test="${not empty errorMsgs['emid']}">
   <div class="col-sm-offset-2 col-sm-8 error_msg2" id="error_msg">
  	${errorMsgs['emid']}
   </div>
  </c:if>
  <!-- 密碼為空 -->
  <c:if test="${not empty errorMsgs['password']}">
   <div class="col-sm-offset-2 col-sm-8 error_msg2" id="error_msg"> 
  	${errorMsgs['password']}
   </div>
 </c:if>

  <div class="form-group">
    <div class="col-sm-offset-1 col-sm-10">
      <input type="text" class="form-control login-form" id="emid" name="emid" placeholder="請輸入員編" required>
    </div>
    
  </div>
  
  <div class="form-group">
    <div class="col-sm-offset-1 col-sm-10">
      <input type="password" class="form-control login-form" id="password" name="password" placeholder="請輸入密碼" required>
    </div>
    
  </div>
  <div class="form-group">
    <div class="col-sm-offset-2 col-sm-8">
      <br>
      <button type="submit" class="btn btn-login2 btn-lg btn-block">登入</button>
      <a href="ForgetPwd.jsp" class="forPwd-link">忘記密碼</a>
    </div>
  </div>
</form>
</div>
</div>


  <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  
</body>
</html>
