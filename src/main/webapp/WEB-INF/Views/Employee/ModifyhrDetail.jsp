<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<%@ include file="/WEB-INF/Views/Template/header.file"%>
<title>工時系統-填寫工時</title>
</head>
<body>
	<%@ include file="/WEB-INF/Views/Template/EmployeeNav.jsp"%>
	<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
	<div class="container">
		<div class="jumbotron">
			<!--網站主內容-->
			<div class="title">填寫工時</div>
		</div>
		<div class="alert alert-warning col-md-10 col-md-offset-1">
			<p>
				<strong>>本週為年度第${fn:substring(workhour.hsid,6,9) }週(${fn:substring(workhour.hsid,0,4) }/${fn:substring(workhour.hsid,4,6) })下表為本週需填寫工時時數</strong>
			</p>
		</div>
		
		<div class="row">
			<div class="col-sm-12 col-md-10 col-md-offset-1">
				<table class="table">
					<thead>
						<tr>
							<th>星期一</th>
							<th>星期二</th>
							<th>星期三</th>
							<th>星期四</th>
							<th>星期五</th>
							<th>星期六</th>
							<th>星期日</th>
						</tr>
					</thead>
					<tbody>
						<tr>
							<td>${week.mon}</td>
							<td>${week.tues}</td>
							<td>${week.wed}</td>
							<td>${week.thur}</td>
							<td>${week.fri}</td>
							<td>${week.sat}</td>
							<td>${week.sun}</td>
						</tr>
						<c:if test="${!empty hourerrorMsg }">
							<td style="color:red;">${hourerrorMsg.mon}</td>
							<td style="color:red;">${hourerrorMsg.tues}</td>
							<td style="color:red;">${hourerrorMsg.wed}</td>
							<td style="color:red;">${hourerrorMsg.thur}</td>
							<td style="color:red;">${hourerrorMsg.fri}</td>
							<td style="color:red;">${hourerrorMsg.sat}</td>
							<td style="color:red;">${hourerrorMsg.sun}</td>
						</c:if>
					</tbody>
				</table>
			</div>
		</div>
		<div class="row" style="margin:3%;">
			<c:forEach var="workhour" items="${workhourdetail}" varStatus="status">
			<!--表單開始-->


				<div class="form-container" >
					<form action="#" method="post" name="AlterHr">
						<input type='hidden' name='orihscontent' value="${workhour.hscontent }">
						<input type="hidden" name="hsdeid" value="${workhour.hsdeid }">
						<input type="hidden" name="emid" value="${workhour.emid }">
						
						
						<div class="row">
						
							<div class="form-group col-md-2 col-md-offset-1">
								<label for="hscontent${status.index }">請輸入工作內容</label> 
								<input type="text" name="hscontent" class="form-control" placeholder="工作內容" id="hscontent${status.index }" value="${workhour.hscontent }">
								<c:if test="${not empty errorMsgsList[status.index]['hscontent']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['hscontent']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="monhr${status.index }">星期一</label> 
								<input type="number" name="monhr" class="form-control" placeholder="時數" id="monhr${status.index }" value="${workhour.monhr }" min=0 max=8 step= 1>
								<c:if test="${not empty errorMsgsList[status.index]['monhr']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['monhr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="tueshr${status.index }">星期二</label> 
								<input type="number" name="tueshr" class="form-control" placeholder="時數" id="tueshr${status.index }" value="${workhour.tueshr }" min=0 max=8 step= 1>
								<c:if test="${not empty errorMsgsList[status.index]['tueshr']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['tueshr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="wedhr${status.index }">星期三</label> 
								<input type="number" name="wedhr" class="form-control" placeholder="時數" id="wedhr${status.index }" value="${workhour.wedhr }" min=0 max=8 step= 1>
								<c:if test="${not empty errorMsgsList[status.index]['wedhr']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['wedhr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="thurhr${status.index }">星期四</label> 
								<input type="number" name="thurhr" class="form-control" placeholder="時數" id="thurhr${status.index }" value="${workhour.thurhr }" min=0 max=8 step= 1>
								<c:if test="${not empty errorMsgsList[status.index]['thurhr']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['thurhr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="frihr${status.index }">星期五</label> 
								<input type="number" name="frihr" class="form-control" placeholder="時數" id="frihr${status.index }" value="${workhour.frihr }" min=0 max=8 step= 1>
								<c:if test="${not empty errorMsgsList[status.index]['frihr']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['frihr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="sathr${status.index }">星期六</label> 
								<input type="number" name="sathr" class="form-control" placeholder="時數" id="sathr${status.index }" value="${workhour.sathr }" min=0 max=8 step= 1>
								<c:if test="${not empty errorMsgsList[status.index]['sathr']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['sathr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="sunhr${status.index }">星期日</label> 
								<input type="number" name="sunhr" class="form-control" placeholder="時數" id="sunhr${status.index }" value="${workhour.sunhr }" min=0 max=8 step= 1>
								<c:if test="${not empty errorMsgsList[status.index]['sunhr']}">
	    							<p style="color:red; font-size:70%;">${errorMsgsList[status.index]['sunhr']}</p>
								</c:if>
							</div>
						
						</div>
						
						
						
						
						<div class="row">
							
							<div class="form-group col-md-2 col-md-offset-1">

							</div>
							<div class="form-group col-md-1">
							    <label for="monoverhr${status.index }">加班</label> 
							    <input type="number" name="monoverhr" class="form-control" placeholder="時數" id="monoverhr${status.index }" value="${workhour.monoverhr }" min=0 max=8 step= 1>
							    <c:if test="${not empty errorMsgsList[status.index]['monoverhr']}">
							        <p style="color:red; font-size:70%;">${errorMsgsList[status.index]['monoverhr']}</p>
							    </c:if>
							</div>
							<div class="form-group col-md-1">
                                <label for="tuesoverhr${status.index }">加班</label> 
                                <input type="number" name="tuesoverhr" class="form-control" placeholder="時數" id="tuesoverhr${status.index }" value="${workhour.tuesoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty errorMsgsList[status.index]['tuesoverhr']}">
                                    <p style="color:red; font-size:70%;">${errorMsgsList[status.index]['tuesoverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="wedoverhr${status.index }">加班</label> 
                                <input type="number" name="wedoverhr" class="form-control" placeholder="時數" id="wedoverhr${status.index }" value="${workhour.wedoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty errorMsgsList[status.index]['wedoverhr']}">
                                    <p style="color:red; font-size:70%;">${errorMsgsList[status.index]['wedoverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="thuroverhr${status.index }">加班</label> 
                                <input type="number" name="thuroverhr" class="form-control" placeholder="時數" id="thuroverhr${status.index }" value="${workhour.thuroverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty errorMsgsList[status.index]['thuroverhr']}">
                                    <p style="color:red; font-size:70%;">${errorMsgsList[status.index]['thuroverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="frioverhr${status.index }">加班</label> 
                                <input type="number" name="frioverhr" class="form-control" placeholder="時數" id="frioverhr${status.index }" value="${workhour.frioverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty errorMsgsList[status.index]['frioverhr']}">
                                    <p style="color:red; font-size:70%;">${errorMsgsList[status.index]['frioverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="satoverhr${status.index }">加班</label> 
                                <input type="number" name="satoverhr" class="form-control" placeholder="時數" id="satoverhr${status.index }" value="${workhour.satoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty errorMsgsList[status.index]['satoverhr']}">
                                    <p style="color:red; font-size:70%;">${errorMsgsList[status.index]['satoverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="sunoverhr${status.index }">加班</label> 
                                <input type="number" name="sunoverhr" class="form-control" placeholder="時數" id="sunoverhr${status.index }" value="${workhour.sunoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty errorMsgsList[status.index]['sunoverhr']}">
                                    <p style="color:red; font-size:70%;">${errorMsgsList[status.index]['sunoverhr']}</p>
                                </c:if>
                            </div>

							
							
							<div class="col-md-1">
								<button class="btn btn-danger">刪除</button>
							</div>
							
						</div>
						
			
					</form>
					
				</div>
				



			</c:forEach>
			<c:forEach var="workhour" items="${newworkhourdetail}" varStatus="status">
			<!--表單開始-->


				<div class="form-container" >
					<form action="#" method="post" name="WriteHr">
						<input type='hidden' name='orihscontent' value="${workhour.hscontent }">
						<input type="hidden" name="hsdeid" value="${workhour.hsdeid }">
						<input type="hidden" name="emid" value="${workhour.emid }">
						
						
						<div class="row">
						
							<div class="form-group col-md-2 col-md-offset-1">
								<label for="nhscontent${status.index }">請輸入工作內容</label> 
								<input type="text" name="hscontent" class="form-control" placeholder="工作內容" id="nhscontent${status.index }" value="${workhour.hscontent }">
								<c:if test="${not empty newerrorMsgsList[status.index]['hscontent']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['hscontent']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="nmonhr${status.index }">星期一</label> 
								<input type="number" name="monhr" class="form-control" placeholder="時數" id="nmonhr${status.index }" value="${workhour.monhr }" min=0 max=8 step= 1>
								<c:if test="${not empty newerrorMsgsList[status.index]['monhr']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['monhr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="ntueshr${status.index }">星期二</label> 
								<input type="number" name="tueshr" class="form-control" placeholder="時數" id="ntueshr${status.index }" value="${workhour.tueshr }" min=0 max=8 step= 1>
								<c:if test="${not empty newerrorMsgsList[status.index]['tueshr']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['tueshr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="nwedhr${status.index }">星期三</label> 
								<input type="number" name="wedhr" class="form-control" placeholder="時數" id="nwedhr${status.index }" value="${workhour.wedhr }" min=0 max=8 step= 1>
								<c:if test="${not empty newerrorMsgsList[status.index]['wedhr']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['wedhr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="nthurhr${status.index }">星期四</label> 
								<input type="number" name="thurhr" class="form-control" placeholder="時數" id="nthurhr${status.index }" value="${workhour.thurhr }" min=0 max=8 step= 1>
								<c:if test="${not empty newerrorMsgsList[status.index]['thurhr']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['thurhr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="nfrihr${status.index }">星期五</label> 
								<input type="number" name="frihr" class="form-control" placeholder="時數" id="nfrihr${status.index }" value="${workhour.frihr }" min=0 max=8 step= 1>
								<c:if test="${not empty newerrorMsgsList[status.index]['frihr']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['frihr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="nsathr${status.index }">星期六</label> 
								<input type="number" name="sathr" class="form-control" placeholder="時數" id="nsathr${status.index }" value="${workhour.sathr }" min=0 max=8 step= 1>
								<c:if test="${not empty newerrorMsgsList[status.index]['sathr']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['sathr']}</p>
								</c:if>
							</div>
							<div class="form-group col-md-1">
								<label for="nsunhr${status.index }">星期日</label> 
								<input type="number" name="sunhr" class="form-control" placeholder="時數" id="nsunhr${status.index }" value="${workhour.sunhr }" min=0 max=8 step= 1>
								<c:if test="${not empty newerrorMsgsList[status.index]['sunhr']}">
	    							<p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['sunhr']}</p>
								</c:if>
							</div>
						
						</div>
						
						
						
						
						<div class="row">
							
							<div class="form-group col-md-2 col-md-offset-1">

							</div>
							<div class="form-group col-md-1">
							    <label for="nmonoverhr${status.index }">加班</label> 
							    <input type="number" name="monoverhr" class="form-control" placeholder="時數" id="nmonoverhr${status.index }" value="${workhour.monoverhr }" min=0 max=8 step= 1>
							    <c:if test="${not empty newerrorMsgsList[status.index]['monoverhr']}">
							        <p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['monoverhr']}</p>
							    </c:if>
							</div>
							<div class="form-group col-md-1">
                                <label for="ntuesoverhr${status.index }">加班</label> 
                                <input type="number" name="tuesoverhr" class="form-control" placeholder="時數" id="ntuesoverhr${status.index }" value="${workhour.tuesoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty newerrorMsgsList[status.index]['tuesoverhr']}">
                                    <p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['tuesoverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="nwedoverhr${status.index }">加班</label> 
                                <input type="number" name="wedoverhr" class="form-control" placeholder="時數" id="nwedoverhr${status.index }" value="${workhour.wedoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty newerrorMsgsList[status.index]['wedoverhr']}">
                                    <p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['wedoverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="nthuroverhr${status.index }">加班</label> 
                                <input type="number" name="thuroverhr" class="form-control" placeholder="時數" id="nthuroverhr${status.index }" value="${workhour.thuroverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty newerrorMsgsList[status.index]['thuroverhr']}">
                                    <p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['thuroverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="nfrioverhr${status.index }">加班</label> 
                                <input type="number" name="frioverhr" class="form-control" placeholder="時數" id="nfrioverhr${status.index }" value="${workhour.frioverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty newerrorMsgsList[status.index]['frioverhr']}">
                                    <p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['frioverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="nsatoverhr${status.index }">加班</label> 
                                <input type="number" name="satoverhr" class="form-control" placeholder="時數" id="nsatoverhr${status.index }" value="${workhour.satoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty newerrorMsgsList[status.index]['satoverhr']}">
                                    <p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['satoverhr']}</p>
                                </c:if>
                            </div>
							<div class="form-group col-md-1">
                                <label for="nsunoverhr${status.index }">加班</label> 
                                <input type="number" name="sunoverhr" class="form-control" placeholder="時數" id="nsunoverhr${status.index }" value="${workhour.sunoverhr }" min=0 max=8 step= 1>
                                <c:if test="${not empty newerrorMsgsList[status.index]['sunoverhr']}">
                                    <p style="color:red; font-size:70%;">${newerrorMsgsList[status.index]['sunoverhr']}</p>
                                </c:if>
                            </div>

							
							
							<div class="col-md-1">
								<button class="btn btn-danger">刪除</button>
							</div>
							
						</div>
						
			
					</form>
					
				</div>
				



			</c:forEach>
			<c:if test="${empty newworkhourdetail && empty workhourdetail }">
			
			
				<div class="form-container" >
						<form action="#" method="post" name="WriteHr">
							<input type="hidden" name="hsdeid" value="${workhour.hsid }">
							<input type="hidden" name="emid" value="${workhour.emid }">
							
							
							<div class="row">
							
								<div class="form-group col-md-2 col-md-offset-1">
									<label for="nhscontent">請輸入工作內容</label> 
									<input type="text" name="hscontent" class="form-control" placeholder="工作內容" id="nhscontent">								
								</div>
								<div class="form-group col-md-1">
									<label for="nmonhr">星期一</label> 
									<input type="number" name="monhr" class="form-control" placeholder="時數" id="nmonhr" min=0 max=8 step= 1>								
								</div>
								<div class="form-group col-md-1">
									<label for="ntueshr">星期二</label> 
									<input type="number" name="tueshr" class="form-control" placeholder="時數" id="ntueshr" min=0 max=8 step= 1>
								</div>
								<div class="form-group col-md-1">
									<label for="nwedhr">星期三</label> 
									<input type="number" name="wedhr" class="form-control" placeholder="時數" id="nwedhr" min=0 max=8 step= 1>
								</div>
								<div class="form-group col-md-1">
									<label for="nthurhr">星期四</label> 
									<input type="number" name="thurhr" class="form-control" placeholder="時數" id="nthurhr" min=0 max=8 step= 1>							
								</div>
								<div class="form-group col-md-1">
									<label for="nfrihr">星期五</label> 
									<input type="number" name="frihr" class="form-control" placeholder="時數" id="nfrihr" min=0 max=8 step= 1>								
								</div>
								<div class="form-group col-md-1">
									<label for="nsathr">星期六</label> 
									<input type="number" name="sathr" class="form-control" placeholder="時數" id="nsathr" min=0 max=8 step= 1>								
								</div>
								<div class="form-group col-md-1">
									<label for="nsunhr">星期日</label> 
									<input type="number" name="sunhr" class="form-control" placeholder="時數" id="nsunhr" min=0 max=8 step= 1>								
								</div>
							</div>		
							<div class="row">
								<div class="form-group col-md-2 col-md-offset-1">
								</div>
								<div class="form-group col-md-1">
								    <label for="nmonoverhr">加班</label> 
								    <input type="number" name="monoverhr" class="form-control" placeholder="時數" id="nmonoverhr" min=0 max=8 step= 1>
								</div>
								<div class="form-group col-md-1">
	                                <label for="ntuesoverhr">加班</label> 
	                                <input type="number" name="tuesoverhr" class="form-control" placeholder="時數" id="ntuesoverhr" min=0 max=8 step= 1>                                
	                            </div>
								<div class="form-group col-md-1">
	                                <label for="nwedoverhr">加班</label> 
	                                <input type="number" name="wedoverhr" class="form-control" placeholder="時數" id="nwedoverhr" min=0 max=8 step= 1>                                
	                            </div>
								<div class="form-group col-md-1">
	                                <label for="nthuroverhr">加班</label> 
	                                <input type="number" name="thuroverhr" class="form-control" placeholder="時數" id="nthuroverhr" min=0 max=8 step= 1>                                
	                            </div>
								<div class="form-group col-md-1">
	                                <label for="nfrioverhr">加班</label> 
	                                <input type="number" name="frioverhr" class="form-control" placeholder="時數" id="nfrioverhr" min=0 max=8 step= 1>                                
	                            </div>
								<div class="form-group col-md-1">
	                                <label for="nsatoverhr">加班</label> 
	                                <input type="number" name="satoverhr" class="form-control" placeholder="時數" id="nsatoverhr" min=0 max=8 step= 1>                                
	                            </div>
								<div class="form-group col-md-1">
	                                <label for="nsunoverhr">加班</label> 
	                                <input type="number" name="sunoverhr" class="form-control" placeholder="時數" id="nsunoverhr" min=0 max=8 step= 1>                               
	                            </div>		
								<div class="col-md-1">
									<button class="btn btn-danger">刪除</button>
								</div>
							</div>
						</form>		
					</div>
				</div>	
			</c:if>
			
			
		</div>
		

		<!--表單開始-->
		
		
		
		
		
		
		
			
	

		<div class="row" style="height:5%;">
		</div>

		<div class="col-md-3 col-md-offset-5">
			<button id="add" class="btn btn-success">新 增</button>
			<button id="temporay" class="btn btn-info">工時暫存</button>
			<button id="preview" class="btn btn-submit">確定送出</button>
		</div>
		

		
		<!--表單結束-->
		<form action="<%=request.getContextPath()%>/Modifyhr" method="post" id="jsonform">
			<input type='hidden' name='AlterHr' >
			<input type='hidden' name='WriteHr' >
			<input type='hidden' name='action' value='temporay'>
			<input type='hidden' name='hsid' value='${hsid }'>
		</form>
		
	
	<%@ include file="/WEB-INF/Views/Template/footer.jsp"%>
	<script>
		
			$(function() {
				
				<c:if test = "${not empty errorMsg}">
					alert("${errorMsg}");
				</c:if>
				$.extend($.fn, {
				    serializeObject: function() {
				      var o = {};
				      var a = this.serializeArray();
				      $.each(a, function() {
				        if (o[this.name] !== undefined) {
				          if (!o[this.name].push) {
				            o[this.name] = [o[this.name]];
				          }
				          o[this.name].push(this.value || '');
				        } else {
				          o[this.name] = this.value || '';
				        }
				      });
				      return o;
				    }
				  });
			
			
			$('#temporay').on('click',function() {
				var Alter = [];
				var write = [];
				$('form[name="AlterHr"]').each(function() {
					Alter.push($(this).serializeObject());
				});
				$('form[name="WriteHr"]').each(function() {
					write.push($(this).serializeObject());
				});
				$('#jsonform input[name="AlterHr"]').val(JSON.stringify(Alter));
				$('#jsonform input[name="WriteHr"]').val(JSON.stringify(write));
				$('#jsonform').submit();
			});
			$('#add').on("click",function(){
				var num = $('div[class="form-container"]').size()+'0';
				var $tmpForm = $('div[class="form-container"]:first').clone();
				
				$tmpForm.find('p[style="color:red; font-size:70%;"]').remove();
				$tmpForm.find('form').attr('name','WriteHr');
				$tmpForm.find('input').each(function(){
						$(this).attr("id",$(this).attr("id")+num);
					 	if( ($(this).attr("name")) == "hsdeid" )
					 	{
					 		
					 	}else if( ($(this).attr("name")) == "emid" ){
					 		
					 	}else{
					 		$(this).val("");
					 	}
				});
				$tmpForm.find("label").each(function(){
					$(this).attr("for",$(this).attr("for")+num);
				});
				$tmpForm.insertAfter($('div[class="form-container"]:last'));
				
				
				
				
				
				
				
				$('form[name="WriteHr"] button[class="btn btn-danger"]').on("click",function(event){
					event.preventDefault();
					if($(this).parent().parent().parent().attr("name") == "AlterHr")
					{
						$('#jsonform').append("<input type='hidden' name='delete'>");
						$('#jsonform input[name="delete"]:last-child').val($(this).parent().parent().parent().find("input[name='hscontent']").val());
					}
					$(this).parent().parent().parent().remove();
				});
			});
			$('button[class="btn btn-danger"]').on("click",function(event){
				event.preventDefault();
				if($(this).parent().parent().parent().attr("name") == "AlterHr")
				{
					$('#jsonform').append("<input type='hidden' name='delete'>");
					$('#jsonform input[name="delete"]:last-child').val($(this).parent().parent().parent().find("input[name='hscontent']").val());
				}
				$(this).parent().parent().parent().remove();
			});
			$('#preview').on("click",function(){
				var Alter = [];
				var write = [];
				$('form[name="AlterHr"]').each(function() {
					Alter.push($(this).serializeObject());
				});
				$('form[name="WriteHr"]').each(function() {
					write.push($(this).serializeObject());
				});
				$('#jsonform input[name="AlterHr"]').val(JSON.stringify(Alter));
				$('#jsonform input[name="WriteHr"]').val(JSON.stringify(write));
				$('input[name="action"]').val("submit");
				$('#jsonform').submit();
			})
		});
	</script>
</body>
</html>