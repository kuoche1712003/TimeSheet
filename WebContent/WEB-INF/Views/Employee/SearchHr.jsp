<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/Views/Template/header.file"%>

<title>工時系統-查詢工時</title>

</head>
<body>
	<%@ include file="/WEB-INF/Views/Template/EmployeeNav.jsp"%>


	<div class="container">
		<div class="jumbotron">
			<!--網站主內容-->
			<div class="title">員工</div>
		</div>
		<div class="row">
			<div class="alert alert-warning col-md-10 col-md-offset-1"
				role="alert">

				<!--表單開始-->
				<form class="form-inline" action="SearchHr" method="POST">
					<div class="form-group col-md-4">
						<label for="exampleInputDate">請選擇年份</label> 
						<select name="year"	id="year" class="form-control">
							<option disabled selected>請先選擇年份</option>
							<c:forEach var="existyear" items="${existyear }">
								<option value="${existyear }">${existyear }</option>
							</c:forEach>
						</select>
					</div>
					<div class="form-group col-md-3">
						<label for="exampleInputDate">請選擇月份</label> 
						<select name="month" id="month" class="form-control">
							<option disabled selected></option>
						</select>
					</div>
					<input type="hidden" name="action" value="filter">
					<div class="form-group col-md-3">
						<button type="submit" class="btn btn-default">篩選查詢</button>
					</div>
					
				</form>
				<!--表單結束-->
			</div>
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
							<th>細項</th>
						</tr>
					</thead>
					<tbody>
						<% java.util.List list = (java.util.List) request.getAttribute("empheader");%>
						<%@ include file="/WEB-INF/Views/Template/ChangePageSetup.file"%>
						<c:forEach var="empheader" items="${empheader }" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
							<tr>
								<td id='tdhsid'>${empheader.hsid }</td>
								<td>審核通過</td>
								<td>${empheader.needhr }</td>
								<td>${empheader.hr }</td>
								<td>${empheader.overhr }</td>
								<td><button class="btn btn-danger">細項</button></td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
		<% String changePage = "whichpageSH";  %>
		<% String servlet = "SearchHr";%>
		<%@ include file="/WEB-INF/Views/Template/ChangePageDoing.file"%>
		<form action="SearchHr" method="post" id="viewdetail">
			<input type="hidden" name="action" value="viewdetail"> 
			<input type="hidden" name="hsid" id="hsid">
			<input type="hidden" name="whichPage" value="<%=whichPage%>" />
		</form>
<script>
	$(function(){
		$('table button').on('click',function(){
			var hsid = $(this).parent().parent().find('#tdhsid').html();
			$('#hsid').val(hsid);
			$('#viewdetail').submit();
			
		});
		$('#year').on('change',function(){
			$('#month').html('<option disabled selected></option>');
			$.ajax({
				  type: 'POST',
				  url: "SearchHr",
				  data: {
					  action: "getmonth",
					  existyear: $('#year').val()
				  },
				  dataType: "json",
				  success: function(response){
					  $.each(response,function(index,value){
						  $('#month').append('<option value="'+value+'">'+value+'</option>');
					  });
				  }
			});
			
			
		});
		$('#whichPage').on('change', function() {
        	location.href = '<%=servlet%>?action=<%=changePage%>&whichPage=' + $(this).val();
      	});
	});



</script>



	</div>
	<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>
</body>
</html>


