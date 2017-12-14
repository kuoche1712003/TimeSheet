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
			<div class="row"> 
			<div class="alert alert-warning col-md-10 col-md-offset-1" role="alert">
				<form id="filtForm" class="form-inline" action="ManagerPageController" method="post">
					<div class="row">
						<div class=" col-md-3">
							<% request.getAttribute("exyearList"); %>
							<% request.getAttribute("yearSelect"); %>
							<% request.getAttribute("monthSelect"); %>
							<label for="year">請選擇年份</label> 
							<select name="year" id="year" class="form-control">
								<c:forEach var="exyearStr" items="${exyearList}" begin="0" end="${exyearList.size()}">
									<option value="${exyearStr}" ${yearSelect == exyearStr ? 'selected' : ''} >${exyearStr}</option>
								</c:forEach>				
							</select>
						</div>
						<div class=" col-md-3">
							<% request.getAttribute("exmonList"); %>
							<label for="month">請選擇月份</label> 
							<select name="month" id="month" class="form-control">
							<!-- ajax抓值 -->
							<c:forEach var="exmonStr" items="${exmonList}" begin="0" end="${exmonList.size()}">
									<option value="${exmonStr}" ${monSelect == exmonStr ? 'selected' : ''} >${exmonStr}</option>
							</c:forEach>	
							</select>
						</div>
				
					
						<% java.util.List list = (java.util.List) request.getAttribute("sahrList");%>
					
						<div class="col-md-3">
								
						</div>
					
						<div class="col-md-3">
							<button class="btn btn-default">篩選查詢</button>
	                   		<input id="action" type="hidden" name="action" value="FilterHR" />
							<button id="btnExcel" class="btn btn-default">匯出工時紀錄</button>
						</div>
					
					
					
					</div>
						
					
				</form>
			</div>
			</div>
		
			<div class="row"> 
				<div class="panel panel-default">
					<div class="panel-heading">
		            	<h3 class="panel-title">查詢結果</h3>
		            </div>
					<div class="panel-body" >
						<table class="table table-hover">
							<thead>
								<tr>
									<th>週別編號</th>
									<th>員工編號</th>
									<th>員工姓名</th>
									<th>本週應填時數</th>
									<th>本週實際時數</th>
									<th>加班總計</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								
								<%@ include file="/WEB-INF/Views/Template/ChangePageSetup.file" %>
								<c:forEach var="sahrVO" items="${sahrList}" begin="<%=pageIndex%>" end="<%=pageIndex+pageSize-1%>">
									<tr>
										<td>${sahrVO.hsid}</td>
										<td>${sahrVO.emid}</td>
										<td>${sahrVO.ename}</td>
										<td>${sahrVO.mweekhr}</td>
										<td>${sahrVO.rweekhr}</td>
										<td>${sahrVO.overtime}</td>
										<td>
											<form class="form-inline" action="ManagerPageController" method="post">
												<button  class="btn btn-default">細項</button>
	                   							<input type="hidden" name="action" value="SearHRD" />
	                   							<input type="hidden" name="hsid" value="${sahrVO.hsid}">
	                   							<input type="hidden" name="emid" value="${sahrVO.emid}">
	                   							<input type="hidden" name="whichPage" value="<%=whichPage%>" />
											</form>
										</td>
									</tr>
								</c:forEach>
							</tbody>			
						</table>
			            <%-- 修改 action 指向 Servlet 對應的 URL  --%>
			            <% String changePage = "whichpageSHR";  %>
			            <% String servlet = "ManagerPageController"; %>
						<%@ include file="/WEB-INF/Views/Template/ChangePageDoing.file"%>
			            
					</div>
		
				</div>
			</div>
		</div>
		<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>
	
	  
	    <script>
		    $(function() {
	         	$('#whichPage').on('change', function() {
	            	location.href = '<%=servlet%>?action=<%=changePage%>&whichPage=' + $(this).val();
	          	});
	         });
	    	$(function(){
	    		$('#year').on('change', function(){
	    			$.ajax({
	    				type: "POST",
	    				url: "ManagerPageController",
						data: {
								action : "autoGetMon",
								year : $("#year").val()
								},
						dataType: "json",
					
						success : function(response){
							$.each(response,function(index, value){
								if(index==0)
									$("#month").html('<option value="' + value + '" ${monSelect} == ' +value + " ? 'selected' : ''>" +value+"</option>");
								else
									$("#month").append("<option value=\"" + value + "\" ${monSelect} == " + value + " ? 'selected' : ''>" +value+"</option>");
							});
						},
						error : function(xhr, ajaxOptions, thrownError){
							alert(xhr.status+"\n"+thrownError);
						}
	    			});
	    		});
	    		
	    		$('#btnExcel').on('click',function(){
	    			
	    			$.blockUI({ message: '<div>郵件寄送費時，請耐心等待。</div><br><div>寄送後將自動進行頁面跳轉。</div>'});
	    			  
	    			$('#action').val("exportExcel");
	    			 
	    		});
	    		
				
	    		
	    	});
	    </script>
	</body>
	
</html>