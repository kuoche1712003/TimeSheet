<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
     <%@ include file="/WEB-INF/Views/Template/header.file" %>
   
    <title>工時系統-設定權限</title>
    
  </head>
  <body>
    <%@ include file="/WEB-INF/Views/Template/AdminNav.jsp" %>


<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">設定權限</div>
  </div>

    <div class="alert alert-warning col-md-6 col-md-offset-3">
       <p>>輸入<strong>權限名</strong>以查找該權限完整人員清單。</p>
       <p>>輸入<strong>員工編號/姓名/身分證</strong>檢索單一員工資料。</p>
   </div>

	<!--表單開始-->
  <div class="form-container">
   <form action="SearchAuthorityController" name="Search_SetAuthority" id="Search_SetAuthority" method="post">
  <div class="form-group col-md-5 col-md-offset-3">
    <input type="text"  name="search_authority" id="search_authority" class="form-control" placeholder="請輸入檢索關鍵字" required>
  </div>
  <div class="form-group col-md-1">
    <button type="submit" id="Sure" class="btn btn-submit btn-block">查詢</button>
  </div>
  <c:if test="${not empty errorMsgs['Emid']}">
  	<div class="col-md-3 error_msg" id="error_msg">
  		${errorMsgs['Emid']}
  	</div>
  </c:if>
  <input type="hidden" name="action" id="name" value="query"/>
 </form>
</div>
   <!--表單結束-->
   <br>
<div class="row">
<div class="col-md-12">
<table class="table table-hover">
    <tr>
        <td>員工編號</td>
        <td>員工姓名</td>
        <td>身份證字號</td>
        <td>權限</td>
        <td></td>
    </tr>

	    
    <c:forEach items="${empList}" var="empVO" varStatus="st">
	    <c:if test="${empVO.autid == 1}">
	       <c:set var = "autid_str" scope = "session" value = "${'主管'}"/>
	    </c:if>
	    <c:if test="${empVO.autid == 2}">
	       <c:set var = "autid_str" scope = "session" value = "${'員工'}"/>
	    </c:if>  
	    <c:if test="${empVO.autid == 3}">
	      <c:set var = "autid_str" scope = "session" value = "${'未設定'}"/>
	    </c:if> 
        <tr>
            <td>${empVO.emid}</td>
            <td>${empVO.name}</td>
            <td>${empVO.id}</td>
            <td><c:out value = "${autid_str}"/></td>
            <td><form action="SearchAuthorityController" method="post">
                      <button class="btn btn-info">更新</button>
                      <input type="hidden" name="action" id="action" value="preUpdate" />
                      <input type="hidden" name="emid" id="emid" value="${empVO.emid}" />
                      <input type="hidden" name="name" id="name" value="${empVO.name}" />
                </form>
            </td>
        </tr>
    </c:forEach>
</table>
 </div>  
 </div> 
 <!-- 修改權限成功訊息 -->
  <c:if test="${not empty Msgs['success']}">
   <script>
	alert("${Msgs['success']}");
   </script>
  </c:if>
 
</div>
   <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>