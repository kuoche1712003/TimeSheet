<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-static-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        </button> 
        <a class="navbar-brand" href="<%=application.getContextPath()%>/ManagerPageController?action=ManagerHome">工時系統</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse navbar-right">
      <ul class="nav navbar-nav">
        <li><a href="<%=application.getContextPath()%>/ManagerPageController?action=Review">審核工時</a></li>
        <li><a href="<%=application.getContextPath()%>/ManagerPageController?action=Urge">催交工時</a></li>
        <li><a href="<%=application.getContextPath()%>/ManagerPageController?action=SearchEmployee">查詢工時</a></li>
        <li><a href="<%=application.getContextPath()%>/ManagerPageController?action=EditPwdMan">修改密碼</a>
         <li><a href="<%=application.getContextPath()%>/LogoutController"  role="button" aria-expanded="false">登出</a></li>

      </ul>
    </div>
  </div>
</nav>