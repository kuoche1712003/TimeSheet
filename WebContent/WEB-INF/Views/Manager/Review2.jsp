<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
	<head>
		<%@ include file="/WEB-INF/Views/Template/header.file"%>
		
		<title>工時系統-審核工時</title>
	
	</head>
	<body>


		<!-- Model -->
		<div class="modal fade" id="returnReasonModal" tabindex="-1" role="dialog" aria-labelledby="returnReasonModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<h4 class="modal-title" id="returnReasonModalLabel">退回原因</h4>
					</div>
					<div class="modal-body">
						<form>
							<div class="form-group">
								<label for="message-text" class="control-label">原因:</label>
								<textarea class="form-control" id="message-text"></textarea>
							</div>
						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button id="modalBtn" name="modalBtn" type="button" class="btn btn-primary" data-dismiss="modal">寄送</button>
					</div>
				</div>
			</div>
		</div>
			
	
	
		<%@ include file="/WEB-INF/Views/Template/ManagerNav.jsp"%>

		<div class="container">
			<div class="jumbotron">
				<div class="title">審核工時</div>
			</div>
			
		<div class="alert alert-success col-md-10 col-md-offset-1" style="font-size: 18px;">
			<form class="form-inline" id="submitForm" action="ManagerPageController" method="post">
				<table class="table table-striped">
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
						<%	request.getAttribute("rvhrVO");	%>
						<tr>
							<td>${rvhrVO.hsid}</td>
							<td>${rvhrVO.emid}</td>
							<td>${rvhrVO.ename}</td>
							<td>${rvhrVO.mweekhr}</td>
							<td>${rvhrVO.rweekhr}</td>
							<td>
								<select id="status" name="status">
									<option value="1">已審核</option>
									<option value="3">已退回</option>
								</select>
							</td>
							<td>${rvhrVO.reviewtime}</td>
							<td>
								<button class="btn btn-default" id="reviewBtn" name="reviewBtn">進行審核</button>
								<input type="hidden" name="action" value="UpdateReview">
								<input type="hidden" name="hsid" value="${rvhrVO.hsid}">
								<input type="hidden" name="emid" value="${rvhrVO.emid}">
								<input type="hidden" name="ename" value="${rvhrVO.ename}">
								<input type="hidden" id="reasonMsg" name="reasonMsg" 	>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
		
		<br><br><br><br><br><br><br><br>
		<div class="panel panel-default">
				<button id="btnBack" class="btn btn-outline-success" style="float:right;"  >返回</button>
				<div class="panel-heading">
	            	<h3 class="panel-title">工時細項</h3>
	            </div>
	            
				<div class="panel-body">
					<table class="table table-striped">
						<% request.getAttribute("rvOhrList"); %>
						<c:forEach var="rvOhrVO" items="${rvOhrList}" begin="0" end="${rvOhrList.size()}">
							<thead style="border:1px #000000 solid;">
								<tr>
									<th style="font-size: 18px; background-color: #FFFFE4;">工作內容</th>
									<th>週一時數</th>
									<th>週二時數</th>
									<th>週三時數</th>
									<th>週四時數</th>
									<th>週五時數</th>
									<th>週六時數</th>
									<th>週日時數</th>
								</tr>
							
								<tr>
									<td style="font-size: 18px; background-color: #FFFFE4;">${rvOhrVO.hscontent}</td>
									<td>${rvOhrVO.monhr}</td>
									<td>${rvOhrVO.tueshr}</td>
									<td>${rvOhrVO.wedhr}</td>
									<td>${rvOhrVO.thurhr}</td>
									<td>${rvOhrVO.frihr}</td>
									<td>${rvOhrVO.sathr}</td>
									<td>${rvOhrVO.sunhr}</td>
								</tr>
							
								<tr>
									<th style="font-size: 18px; background-color: #FFFFE4;">加班時數</th>
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
									<td>${rvOhrVO.monoverhr}</td>
									<td>${rvOhrVO.tuesoverhr}</td>
									<td>${rvOhrVO.wedoverhr}</td>
									<td>${rvOhrVO.thuroverhr}</td>
									<td>${rvOhrVO.frioverhr}</td>
									<td>${rvOhrVO.satoverhr}</td>
									<td>${rvOhrVO.sunoverhr}</td>
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
				$('#reviewBtn').on('click',function(event){
					event.preventDefault();
					var $status = $('#status');
					if($status.val() == 3){
						$('#reviewBtn').attr('data-toggle','modal');
						$('#reviewBtn').attr('data-target','#returnReasonModal');
					}else{
						$('#submitForm').submit();
					}
				});
			
				$('#modalBtn').on('click',function(){
					var $reasonMsg = $('#message-text').val();
					$('#reasonMsg').val($reasonMsg);
					
					$.blockUI({ message: '<div>郵件寄送費時，請耐心等待。</div><br><div>寄送後將自動進行頁面跳轉。</div>'}); 
					$('#submitForm').submit();
				});
				
				$('#btnBack').on('click',function(){
					location.href = 'ManagerPageController?action=whichpageRHR&whichPage=' + ${param.whichPage};
				});
			});
		</script>
	</body>
</html>