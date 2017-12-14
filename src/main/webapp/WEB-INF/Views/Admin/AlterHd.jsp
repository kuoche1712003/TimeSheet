<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html lang="en">
  <head>
   	  <%@ include file="/WEB-INF/Views/Template/header.file" %>
   	 
    <title>工時系統-修改假日</title>
   
  </head>
  <body>
    <%@ include file="/WEB-INF/Views/Template/AdminNav.jsp" %>

<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">修改假日</div>
  </div>

	<!--表單開始-->
  <div class="form-container">
   <form action="HolidayController" name="AlterHdForm" id="AlterHdForm" method="post">
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="exampleInputDate">請輸入欲修改日期</label>
    <!-- 錯誤訊息 -->
    <c:if test="${not empty errorMsgs['error']}">
   	    <span class="error_msg">
  			${errorMsgs['error']}
 	    </span>
  	</c:if>
  	<!-- 錯誤訊息結束 -->
    <input type="date" name="hddate" id="hddate" class="form-control" required>
  </div>
  <div class="form-group col-md-3 col-md-offset-5">
    <button type="reset" id="ReWrite" class="btn btn-danger">重新填寫</button>
    <button type="submit" id="Sure" class="btn btn-submit">確定送出</button>
  </div>
  	   <input type="hidden" name="action" value="alterhd">
 </form>
</div>
   <!--表單結束-->
 

</div>
    <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>