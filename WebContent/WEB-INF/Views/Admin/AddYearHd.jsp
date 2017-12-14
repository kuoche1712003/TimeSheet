<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  
<!DOCTYPE html>
<html lang="en">
  <head>
   	  <%@ include file="/WEB-INF/Views/Template/header.file" %>
   	 
    <title>工時系統-新增年度假日</title>
   
  </head>
  <body>
    <%@ include file="/WEB-INF/Views/Template/AdminNav.jsp" %>


<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">新增年度假日</div>
  </div>

	<!--表單開始-->
  <div class="form-container">
   <form action="HolidayController" name="AddYearHdForm" id="AddYearHdForm" method="post">
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="exampleInputYear">請輸入欲新增年度</label>
    <!-- 錯誤訊息 -->
    <c:if test="${not empty errorMsgs['prestatus']}">
    	<span class="error_msg">
  			${errorMsgs['prestatus']}
  	    </span>
  	</c:if>
  	<c:if test="${not empty Msgs['status']}">
  	    <span class="error_msg">
  			${Msgs['status']}
  		</span>
  	</c:if>
  	<!-- 錯誤訊息結束 -->
    <input type="text" name="year" id="year" class="form-control" placeholder="請輸入年度 EX 2018" required>
    <div class="row">
    </div>
  </div>
  
  <div class="form-group col-md-3 col-md-offset-5">
    <button type="reset" id="ReWrite" class="btn btn-danger">重新填寫</button>
    <button type="submit" id="Submit" class="btn btn-submit">確定送出</button>
  </div>
   <input type="hidden" name="action" value="addyearhd">
 </form>
  </div>
</div>
   <!--表單結束-->

    <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>