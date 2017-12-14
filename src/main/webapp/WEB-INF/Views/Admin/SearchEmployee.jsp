<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html lang="en">
  <head>
  	  <%@ include file="/WEB-INF/Views/Template/header.file" %>
  	  
    <title>工時系統-修改員工資料</title>
  
  </head>
  <body>
    <%@ include file="/WEB-INF/Views/Template/AdminNav.jsp" %>


<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">修改員工資料</div>
  </div>
	<!--表單開始-->
  <div class="form-container">
   <form action="EmployeeController" name="Search_Employee" id="Search_Employee" method="post">
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="search_employee">請輸入欲修改員編</label>
      <!-- 錯誤訊息 -->
    <c:if test="${not empty errorMsgs['errorMsgs']}">
   	    <span class="error_msg">
  			${errorMsgs['errorMsgs']}
 	    </span>
  	</c:if>
  	 <c:if test="${not empty errorMsgs['error']}">
   	    <span class="error_msg">
  			${errorMsgs['error']}
 	    </span>
  	</c:if>
  	<!-- 錯誤訊息結束 -->
    <input type="text" name="search_employee" id="search_employee" class="form-control" placeholder="員工編號">
  </div>
  <div class="form-group col-md-4 col-md-offset-4">
    <button type="submit" id="Sure" class="btn btn-submit btn-block">查詢</button>
  </div>
  <input type="hidden" name="action" value="searemp">
 </form>
</div>
   <!--表單結束-->
 

</div>
    <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>