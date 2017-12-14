<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
  <%@ page import="JDBC.*" %> 
  
<!DOCTYPE html>
<html lang="en">
  <head>
    
    <title>工時系統</title>
<%@ include file="/WEB-INF/Views/Template/header.file"%>

  </head>
  <body>
	

	

<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">更新密碼</div>
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
     <input type="hidden" name="action" value="starteditpwd">
     <input type="hidden" name="emid" value="<%=request.getParameter("emid")%>">
 </form>
 
</div>
   <!--表單結束-->

</div>
    <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>




