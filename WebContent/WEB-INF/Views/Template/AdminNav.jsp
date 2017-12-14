<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<nav class="navbar navbar-inverse navbar-static-top" role="navigation">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar"> <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        </button> 
        <a class="navbar-brand" href="<%=application.getContextPath()%>/AdminPageController?action=AdminHome">工時系統</a>
    </div>
    <div id="navbar" class="collapse navbar-collapse navbar-right">
     
      <ul class="nav navbar-nav">
        <li><a href="#" data-toggle="dropdown" role="button" aria-expanded="false">設定假日<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="<%=application.getContextPath()%>/AdminPageController?action=AddYearHd">新增年度假日</a></li>
              <li><a href="<%=application.getContextPath()%>/AdminPageController?action=AddHd">新增假日</a></li>
              <li><a href="<%=application.getContextPath()%>/AdminPageController?action=AlterHd">修改假日</a></li>
            </ul>
     
        </li>
        <li><a href="#" data-toggle="dropdown" role="button" aria-expanded="false">設定員工<span class="caret"></span></a>
            <ul class="dropdown-menu" role="menu">
              <li><a href="<%=application.getContextPath()%>/AdminPageController?action=AddEmployee">新增員工</a></li>
              <li><a href="<%=application.getContextPath()%>/AdminPageController?action=SearchEmployee">修改員工</a></li>
            </ul>
        </li>
        <li><a href="<%=application.getContextPath()%>/AdminPageController?action=SearchSetAuthority">設定權限</a> </li> 
        
             
         <li><a href="<%=application.getContextPath()%>/LogoutController"  role="button" aria-expanded="false">登出</a></li>
        
            </ul>
            
        </li>
        
      </ul>
      
    </div>
  </div>
</nav>