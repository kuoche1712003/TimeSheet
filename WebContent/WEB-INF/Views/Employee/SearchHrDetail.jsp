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
		<c:forEach var="workhourdetail" items="${workhourdetail }">
			<div class="form-container">
				<div class="row">

				<div class="form-group col-md-2 col-md-offset-1">
					<label> 工作內容 </label>
					<div>${workhourdetail.hscontent }</div>
				</div>
				<div class="form-group col-md-1">
					<label> 星期一 </label>
					<div>${workhourdetail.monhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label> 星期二 </label>
					<div>${workhourdetail.tueshr }</div>
				</div>
				<div class="form-group col-md-1">
					<label> 星期三 </label>
					<div>${workhourdetail.wedhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label> 星期四 </label>
					<div>${workhourdetail.thurhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label> 星期五 </label>
					<div>${workhourdetail.frihr }</div>
				</div>
				<div class="form-group col-md-1">
					<label> 星期六 </label>
					<div>${workhourdetail.sathr }</div>
				</div>
				<div class="form-group col-md-1">
					<label> 星期日 </label>
					<div>${workhourdetail.sunhr }</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group col-md-2 col-md-offset-1"></div>
				<div class="form-group col-md-1">
					<label>加班</label>
					<div>${workhourdetail.monoverhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label>加班</label>
					<div>${workhourdetail.tuesoverhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label>加班</label>
					<div>${workhourdetail.wedoverhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label>加班</label>
					<div>${workhourdetail.thuroverhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label>加班</label>
					<div>${workhourdetail.frioverhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label>加班</label>
					<div>${workhourdetail.satoverhr }</div>
				</div>
				<div class="form-group col-md-1">
					<label>加班</label>
					<div>${workhourdetail.sunoverhr }</div>
				</div>



			</div>
			<div class="row">
				<div class="col-md-9 col-md-offset-1">
					<HR>
				</div>
			</div>
			</div>
		</c:forEach>
	</div>







	</div>
	<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>
</body>
</html>


