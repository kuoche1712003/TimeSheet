<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
  <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
  
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
  <c:if test="${not empty errorMsgs['error']}">
  	<div class="form-group col-md-6 col-md-offset-3 error_msg2" id="error_msg">
  		${errorMsgs['error']}
  	</div>
  </c:if>
   <form action="HolidayController" name="AlterHdWriteForm" id="AlterHdWriteForm" method="post">
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="hddate">日期</label>
    <input type="hidden" name="hddate" id="hddate" class="form-control" value="${hd.dateString}">
        <p class="form-control-static">${fn:substring(hd.dateString,0,4)} 年 ${fn:substring(hd.dateString,4,6)} 月 ${fn:substring(hd.dateString,6,8)} 日</p>
    
  </div>
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="hdtype">請選擇假別</label>
    <select name="hdtype" id="hdtype" class="form-control">
				<option value="3" ${3 == hd.hdtype ? 'selected' : ''}>颱風假</option>
				<option value="2" ${2 == hd.hdtype ? 'selected' : ''}>國定假日</option>
				<option value="1" ${1 == hd.hdtype ? 'selected' : ''}>例假日</option>
	</select>
  </div>
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="hdhour">請選擇時數</label>
    <input type="number" name="hdhour" id="hdhour" class="form-control" placeholder="請選擇時數" value="${hd.hdhr}" required>
  </div>
  <div class="form-group col-md-3 col-md-offset-5">
    <button type="reset" id="ReWrite" class="btn btn-danger">重新填寫</button>
    <button type="submit" id="Submit" class="btn btn-submit">確定送出</button>
  </div>
   <input type="hidden" name="action" value="alterhdwrite">
 </form>
</div>
   <!--表單結束-->
 

</div>
   <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>