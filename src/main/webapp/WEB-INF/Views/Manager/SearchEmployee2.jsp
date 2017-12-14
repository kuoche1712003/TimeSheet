<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/Views/Template/header.file"%>
		
		<title>工時系統</title>
	
	</head>
	<body>
		<%@ include file="/WEB-INF/Views/Template/ManagerNav.jsp"%>
		<div class="container">
			<div class="jumbotron">
				<div class="title">查詢工時</div>
			</div>
			<button id="btnBack" class="btn btn-outline-success" style="float:right;"  >返回</button>
			<div class="panel panel-default">
				<div class="panel-heading"> 
	            	<h3 class="panel-title" >工時細項</h3>
	            </div>
				<div class="panel-body">
					<table class="table table-striped">
						<% request.getAttribute("sohrList"); %>
						<c:forEach var="sohrVO" items="${sohrList}" begin="0" end="${sohrList.size()}">
							<thead style="border:1px #000000 solid;">
								<tr>
									<th style="background-color: #FFFFE4;">週別編號</th>
									<th style="background-color: #FFFFE4;">員工編號</th>
									<th style="background-color: #FFFFE4;">員工姓名</th>
									<th style="background-color: #FFFFE4;">工作內容</th> 
									<th>週一時數</th>
									<th>週二時數</th>
									<th>週三時數</th>
									<th>週四時數</th>
									<th>週五時數</th>
									<th>週六時數</th>
									<th>週日時數</th>
								</tr>
							
								<tr>
									<td style="background-color: #FFFFE4;">${sohrVO.hsid}</td>
									<td style="background-color: #FFFFE4;">${sohrVO.emid}</td>
									<td style="background-color: #FFFFE4;">${sohrVO.ename}</td>
									<td style="background-color: #FFFFE4;">${sohrVO.hscontent}</td>
									<td>${sohrVO.monhr}</td>
									<td>${sohrVO.tueshr}</td>
									<td>${sohrVO.wedhr}</td>
									<td>${sohrVO.thurhr}</td>
									<td>${sohrVO.frihr}</td>
									<td>${sohrVO.sathr}</td>
									<td>${sohrVO.sunhr}</td>
								</tr>
							
								<tr>
									<th style="background-color: #FFFFE4;">加</th>
									<th style="background-color: #FFFFE4;">班</th>
									<th style="background-color: #FFFFE4;">時</th>
									<th style="background-color: #FFFFE4;">數</th>
									<th>週一時數</th>
									<th>週二時數</th>
									<th>週三時數</th>
									<th>週四時數</th>
									<th>週五時數</th>
									<th>週六時數</th>
									<th>週日時數</th>
								</tr>
							
								<tr>
									<td style="background-color: #FFFFE4;"></td>
									<td style="background-color: #FFFFE4;"></td>
									<td style="background-color: #FFFFE4;"></td>
									<td style="background-color: #FFFFE4;"></td>
									<td>${sohrVO.monoverhr}</td>
									<td>${sohrVO.tuesoverhr}</td>
									<td>${sohrVO.wedoverhr}</td>
									<td>${sohrVO.thuroverhr}</td>
									<td>${sohrVO.frioverhr}</td>
									<td>${sohrVO.satoverhr}</td>
									<td>${sohrVO.sunoverhr}</td>
								</tr>
							</thead>
							<thead style="background-color:#EAFFE4;">
								<tr>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
									<td></td>
								</tr>
							</thead>
						</c:forEach>
					</table>
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>
		<script>
			$(function(){
				$('#btnBack').on('click',function(){
					location.href = 'ManagerPageController?action=whichpageSHR&whichPage=' + ${param.whichPage};
				});
			});
		</script>
	</body>

</html>