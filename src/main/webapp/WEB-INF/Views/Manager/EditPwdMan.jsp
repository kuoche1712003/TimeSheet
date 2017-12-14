<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html lang="en">
  <head>
   
    <title>工時系統</title>
     <%@ include file="/WEB-INF/Views/Template/header.file" %>
    
  </head>
  <body>
 <% 
 if(request.getParameter("editpwd_status")!=null){
	 int editpwd_status =Integer.parseInt(request.getParameter("editpwd_status"));
	 if(editpwd_status == 2){ 
	 	%>
<script>
	alert("密碼更新失敗");
</script>
<%
	 }else if(editpwd_status == 3){
%> 
<script>
	alert("新舊密碼不得相同");
</script>
<%
	 }else if(editpwd_status == 4){
%> 
<script>
	alert("兩次新密碼不相同");
</script>
<%
	 }else if(editpwd_status == 5){
%>
<script>
	alert("舊密碼錯誤");
</script>
<% }else{

%>
<script>
	alert("系統錯誤");
</script>
<%
}}
%>

 <%@ include file="/WEB-INF/Views/Template/ManagerNav.jsp" %>

<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">修改密碼</div>
  </div>
  <!--表單開始-->
  <div class="form-container">
  <!-- 錯誤訊息 -->
   <c:if test="${not empty Msgs['error']}">
  	<div class="form-group col-md-6 col-md-offset-3 error_msg2" id="error_msg">
  		${Msgs['error']}
  	</div>
  </c:if>
  <!-- 錯誤訊息結束 -->
   <form action="EditPwdController" name="EditPwdController" id="EditPwdController" method="post">
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="password">請輸入舊密碼</label>
    <input type="password" name="password" id="password" class="form-control" placeholder="舊密碼" required>
  </div>
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="newpassword">請輸入新密碼</label>
    <input type="password" name="newpassword" id="newpassword" class="form-control" placeholder="新密碼" required>
  </div>
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="newpassword2">再次輸入新密碼</label>
    <input type="password" name="newpassword2" id="newpassword2" class="form-control" placeholder="新密碼" required> 
  </div>

  <div class="form-group col-md-3 col-md-offset-5">
    <button type="reset" id="ReWrite" class="btn btn-danger">重新填寫</button>
    <button type="submit" id="Submit" class="btn btn-submit">確定送出</button>
  </div>
  <c:if test="${not empty Msgs['error']}">
  	<div class="col-md-3 error_msg" id="error_msg">
  		${Msgs['error']}
  	</div>
  </c:if>
       <input type="hidden" name="action" value="editpwdman">
  
 </form>
</div>
   <!--表單結束-->

</div>
    <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>




