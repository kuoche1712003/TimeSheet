<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
  <head>
      <%@ include file="/WEB-INF/Views/Template/header.file" %>
    
    <title>工時系統-填寫工時</title>
    
  </head>
  <body>
    <%@ include file="/WEB-INF/Views/Template/EmployeeNav.jsp" %>


<div class="container">
  <div class="jumbotron"> <!--網站主內容-->
       <div class="title">填寫工時</div>
  </div>
   <div class="alert alert-warning col-md-10 col-md-offset-1" role="alert">
       <p>><strong>2017/05/01-07(第一週)</strong></p>
   </div>

  <!--表單開始-->
  <div class="form-container">
   <form action="#" method="post" name="WriteHr" id="WriteHr">
   <div class="form-group col-md-2 col-md-offset-1  has-success">
    <label for="exampleInputTimeContent">請輸入工作內容</label>
    <p class="form-control-static">資料庫維護</p>
  </div>
  <div class="form-group col-md-1">
    <label for="exampleInputTime ">星期一</label>
    <input type="number" name="hour" class="form-control" placeholder="時數" value="4" disabled>
  </div>
  <div class="form-group col-md-1">
    <label for="exampleInputTime ">星期二</label>
    <input type="number" name="hour" class="form-control" placeholder="時數" value="4" disabled>
  </div>
  <div class="form-group col-md-1">
    <label for="exampleInputTime ">星期三</label>
    <input type="number" name="hour"class="form-control" placeholder="時數" value="4" disabled>
  </div>
  <div class="form-group col-md-1">
    <label for="exampleInputTime ">星期四</label>
    <input type="number" name="hour" class="form-control" placeholder="時數" value="4" disabled>
  </div>
  <div class="form-group col-md-1">
    <label for="exampleInputTime ">星期五</label>
    <input type="number" name="hour"class="form-control" placeholder="時數" value="4" disabled>
  </div>
  <div class="form-group col-md-1">
    <label for="exampleInputTime ">星期六</label>
    <input type="number" name="hour"class="form-control" placeholder="時數" disabled>
  </div>
  <div class="form-group col-md-1 ">
    <label for="exampleInputTime ">星期日</label>
    <input type="number" name="hour"class="form-control" placeholder="時數" disabled>
  </div>
  
  <div class="form-group col-md-3 col-md-offset-2">
    <button id="temporay" class="btn btn-info">工時暫存</button>
    <button type="submit" id="preview" class="btn btn-submit">確定送出</button>
  </div>
 </form>
</div>
   <!--表單結束-->


</div>
   <%@ include file="/WEB-INF/Views/Template/footer.jsp" %>

  </body>
</html>