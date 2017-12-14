<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="/WEB-INF/Views/Template/header.file" %>
    
    <title>工時系統-設定權限</title>
    
  </head>
 <% if(session.getAttribute("Employee") ==null){
	  
		response.sendRedirect("index.jsp?#Login");
		
  }
  	
 	int autid =Integer.parseInt(session.getAttribute("Autid").toString());
 	if(autid==0){
 %>
  <body>
    <%@ include file="/WEB-INF/Views/Template/AdminNav.jsp" %>

<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">設定權限</div>
  </div>

	<!--表單開始-->
  <div class="form-container">
   <form action="SearchAuthorityController" name="AuthoritySetForm" id="AuthoritySetForm" method="post">
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="exampleCNum">員工編號</label>
    <!-- 後端讀入員工編號 -->
    <input type="hidden" id="Emid" name="Emid" value="${empVO.emid}">
    <p class="form-control-static">${empVO.emid}</p>
  </div>
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="exampleCNum">姓名</label>
    <!-- 後端讀入姓名 -->
    <p class="form-control-static">${empVO.name}</p>
  </div>
  <div class="form-group col-md-6 col-md-offset-3">
    <label for="exampleInputDate">請選擇權限</label>
    <select name="authority" id="authority" class="form-control">
				<option value="3" ${3 == empVO.autid ? 'selected' : ''}>未設定</option>
				<option value="1" ${1 == empVO.autid ? 'selected' : ''}>主管</option>
				<option value="2" ${2 == empVO.autid ? 'selected' : ''}>員工</option>
	</select>
  </div>
  <div class="col-md-3 col-md-offset-5">
    <input type="hidden" name="action" id="action" value="update" />
    <button type="reset" id="ReWrite" class="btn btn-danger">重新填寫</button>
    <button type="submit" id="AuSetSubmit" class="btn btn-submit">確定送出</button>
  </div>
 </form>
</div>
   <!--表單結束-->
   
   
   
 <%
 	}
 %>

</div>
    <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>