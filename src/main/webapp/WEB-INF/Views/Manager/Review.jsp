<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/Views/Template/header.file"%>
		<title>工時系統-審核工時</title>
	</head>
	<body>
		<%@ include file="/WEB-INF/Views/Template/ManagerNav.jsp"%>
	
		<div class="container">
			<div class="jumbotron">
				<div class="title">審核工時</div>
			</div>
			
			<div class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">待審項目</h3>
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
								<th></th>
							</tr>
						</thead>
						<tbody>
							<% java.util.List list = (java.util.List) request.getAttribute("rvhrList");%>
							<%@ include file="/WEB-INF/Views/Template/ChangePageSetup.file"%>
							<c:forEach var="rvhrVO" items="${rvhrList}" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
								<tr>
									<td>${rvhrVO.hsid}</td>
									<td>${rvhrVO.emid}</td>
									<td>${rvhrVO.ename}</td>
									<td>${rvhrVO.mweekhr}</td>
									<td>${rvhrVO.rweekhr}</td>
									<c:choose>
							            <c:when test="${rvhrVO.urgestatus == 2}">
							                <td>未審核</td>
							            </c:when>
							            <c:otherwise>
							                <td>已退回</td>
							            </c:otherwise>
							        </c:choose>
									<td>${rvhrVO.reviewtime}</td>
									<td>
										<form class="form-inline" action="ManagerPageController" method="post">
											<button class="btn btn-default">審核</button>
											<input type="hidden" name="action" value="ReviewHRD" /> 
											<input type="hidden" name="hsid" value="${rvhrVO.hsid}"> 
											<input type="hidden" name="emid" value="${rvhrVO.emid}"> 
											<input type="hidden" name="whichPage" value="<%=whichPage%>" />
										</form>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<%-- 修改 action 指向 Servlet 對應的 URL  --%>
					<% String changePage = "whichpageRHR";  %>
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
	         });
	    </script>
	
		<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>
	
	</body>
</html>