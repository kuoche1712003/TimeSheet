<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <%@ include file="/WEB-INF/Views/Template/header.file" %>
    <title>工時系統</title>
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
       <div class="title">系統管理員</div>
  </div>
  <div class="page-header">
    <h1><small>請選擇功能</small></h1>
    </div>
    
  <!-- 修改例假日成功成功訊息 -->
  <c:if test="${not empty Msgs['success']}">
   <script>
	alert("${Msgs['success']}");
   </script>
  </c:if>
 
<%
}
 if(request.getParameter("employee_status")!=null){
		int employee_status = Integer.parseInt(request.getParameter("employee_status")); 
		if(employee_status == 1){	
			
%>
<script>
	alert("員工新增成功");
</script>
<%
		}}
%>

</div>
   <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>


  
  </body>
</html>