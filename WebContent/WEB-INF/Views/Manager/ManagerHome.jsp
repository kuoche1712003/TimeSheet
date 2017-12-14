<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
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
  	
 	
 %> 
  
 <%@ include file="/WEB-INF/Views/Template/ManagerNav.jsp" %>

<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">主管</div>
  </div>
  <div class="page-header">
    <h1><small>請選擇功能</small></h1>
    </div>
 

</div>
    <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>
 

 
  </body>
</html>