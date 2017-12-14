<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
  <head>
      <%@ include file="/WEB-INF/Views/Template/header.file" %>
    
    <title>工時系統</title>
    
  </head>
  <body>
  <% if(session.getAttribute("Employee") ==null){
	  
		response.sendRedirect("index.jsp?#Login");
		
  }
  	
 	int autid =Integer.parseInt(session.getAttribute("Autid").toString());
 	if(autid==2){
 %> 
  
    <%@ include file="/WEB-INF/Views/Template/EmployeeNav.jsp" %>


<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">員工</div>
  </div>
  <div class="page-header">
  	<c:if test="${not empty complete}">
    	<h3 style="color:red;text-align:center; ">${complete }</h3>
    </c:if>
    <h1><small>請選擇功能</small></h1>
    
   </div>
   
 
<%
 	}
%>
</div>
  <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>
  </body>
  <script>
  	
  	
  	<c:if test="${not empty tmpsave}">
		alert("${tmpsave}");
	</c:if>
	<c:if test="${not empty save}">
		alert("${save}");
	</c:if>
  </script>
</html>