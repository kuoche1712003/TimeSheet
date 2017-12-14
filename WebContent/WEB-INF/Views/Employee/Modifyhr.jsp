<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/Views/Template/header.file"%>

<title>工時系統-修改工時</title>

</head>
<body>
	<%@ include file="/WEB-INF/Views/Template/EmployeeNav.jsp"%>

	<div class="container">
		<div class="jumbotron">
			<!--網站主內容-->
			<div class="title">修改工時</div>
		</div>
		<div class="row">
			<div class="col-xs-offset-1 col-xs-10">
				<table class="table table-hover">
					<thead>
						<tr>
							<th>週別</th>
							<th>狀態</th>
							<th>應填時數</th>
							<th>實填時數</th>
							<th>加班時數</th>
							<th>催交次數</th>
							<th>修改</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="empheader" items="${empheader }">
							<tr>
								<td id='tdhsid'>${empheader.hsid }</td>
								<c:choose>
									<c:when test="${empheader.stdes == 3 && empheader.urgtimes == 0 }">
										<td>退回</td>
									</c:when>
									<c:otherwise>
										<td style="color:red;">催繳</td>
									</c:otherwise>
								</c:choose>
								<td>${empheader.needhr }</td>
								<td>${empheader.hr }</td>
								<td>${empheader.overhr }</td>
								<td>${empheader.urgtimes }</td>
								<td><button class="btn btn-danger">修改</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>

		<form action="Modifyhr" method="post">
			<input type="hidden" name="action" value="viewdetail"> 
			<input type="hidden" name="hsid" id="hsid">
		</form>



	</div>
	<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>

</body>
<script>
	$(function(){
		$('table button').on('click',function(){
			var hsid = $(this).parent().parent().find('#tdhsid').html();
			$('#hsid').val(hsid);
			$('form').submit();
			
		});
	});



</script>





</html>