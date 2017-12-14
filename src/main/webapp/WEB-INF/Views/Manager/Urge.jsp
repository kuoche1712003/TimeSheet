<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/Views/Template/header.file"%>
		<title>工時系統-催交工時</title>
	</head>
	<body>
		<%@ include file="/WEB-INF/Views/Template/ManagerNav.jsp"%>
	
		<div class="container">
			<div class="jumbotron">
				<div class="title">催交工時</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">待催項目</h3>
				</div>
				<div class="panel-body">
					<table class="table table-hover">
						<thead>
							<tr>
								<th>週別編號</th>
								<th>員工編號</th>
								<th>員工姓名</th>
								<th>本週應填時數</th>
								<th>本週實填時數</th>
								<th>審核狀態</th>
								<th>最後審核時間</th>
								<th>催交次數</th>
								<th>最後催交時間</th>
								<th></th>
							</tr>
						</thead>
						<tbody>
							<% java.util.List list = (java.util.List) request.getAttribute("ughrList");%>
							<%@ include file="/WEB-INF/Views/Template/ChangePageSetup.file"%>
							<c:forEach var="ughrVO" items="${ughrList}" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
								<tr>
									<td>${ughrVO.hsid}</td>
									<td>${ughrVO.emid}</td>
									<td>${ughrVO.ename}</td>
									<td>${ughrVO.mweekhr}</td>
									<td>${ughrVO.rweekhr}</td>
									<c:choose>
							            <c:when test="${ughrVO.urgestatus == 3}">
							                <td>已退回</td>
							            </c:when>
							            <c:when test="${ughrVO.urgestatus == 4}">
							                <td>暫存</td>
							            </c:when>
							            <c:when test="${ughrVO.urgestatus == 0}">
							                <td>未填寫</td>
							            </c:when>				
							        </c:choose>
									<td>${ughrVO.reviewtime}</td>
									<td>${ughrVO.urgecnt}</td>
									<td>${ughrVO.urgetime}</td>
									<td>
										<form class="form-inline" action="ManagerPageController" method="post">
											<button id="urgeBtn" class="btn btn-default">催交</button>
											<input type="hidden" name="action" value="UrgeHR" /> 
											<input type="hidden" name="hsid" value="${ughrVO.hsid}"> 
											<input type="hidden" name="ename" value="${ughrVO.ename}"> 
											<input type="hidden" name="emid" value="${ughrVO.emid}"> 
											<input type="hidden" name="urgecnt" value="${ughrVO.urgecnt}"> 
											<input type="hidden" name="whichPage" value="<%=whichPage%>" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%-- 修改 action 指向 Servlet 對應的 URL  --%>
					<% String changePage = "whichpageUHR";  %>
					<% String servlet = "ManagerPageController"; %>
					<%@ include file="/WEB-INF/Views/Template/ChangePageDoing.file"%>
				</div>
			</div>
		</div>
		
	    <script>
	     	 $(function() {
	         	$('#whichPage').on('change', function() {
	            	location.href = '<%=servlet%>?action=<%=changePage%>&whichPage=' + $(this).val();
	          	});
	         	
	         	$('#urgeBtn').on('click', function(){
	         		$.blockUI({ message: '<div>郵件寄送費時，請耐心等待。</div><br><div>寄送後將自動進行頁面跳轉。</div>'});
	         	});
	         });
	     	 
	     	
	    </script>
	
		<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>
	
	</body>
</html>