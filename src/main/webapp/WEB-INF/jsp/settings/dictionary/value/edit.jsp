<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
  String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
  <base href="<%=basePath%>">
  <meta charset="UTF-8">

  <link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet"/>
  <link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css"
        rel="stylesheet"/>

  <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
  <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
  <script>
      $(function () {
          $("#value").on("keyup", function () {
              let value = $.trim($("#value").val());
              if ("" === value) $("#message").prop("style", "color: red").text("字典值不能为空！");
              if (value.length > 0) {
                  $.get(
                      "settings/dictionary/value/getDicValueByValue",
                      {"value": value},
                      function (resp) {
                          if (10000 === resp.success) {
                              $("#message").prop("style", "color: green").text(resp.message);
                          } else {
                              if (value !== "${dicValue.value}")
                                  $("#message").prop("style", "color: red").text(resp.message);
                              else
                                  $("#message").prop("style", "color: green").text("");
                          }
                      }
                  );
              }
          });

          $("#updateBtn").on("click", function () {
              let id = $.trim($("#id").val());
              let typeCode = $.trim($("#typeCode").val());
              let value = $.trim($("#value").val());
              let text = $.trim($("#text").val());
              let orderNo = $.trim($("#orderNo").val());
              let message = $.trim($("#message").text());
              if ("字典值不能为空！" !== message && "该字典值已存在！" !== message) {
                  $.post(
                      "settings/dictionary/value/update",
                      {
                          "id": id,
                          "value": value,
                          "text": text,
                          "orderNo": orderNo,
                          "typeCode": typeCode
                      },
                      function (resp) {
                          if (10000 === resp.success) {
                              location.href = "settings/dictionary/value/index";
                          } else {
                              alert(resp.message);
                          }
                      }
                  );
              } else {
                  alert(message);
                  return false;
              }
          });
      });
  </script>
</head>
<body>

<div style="position:  relative; left: 30px;">
  <h3>修改字典值</h3>
  <div style="position: relative; top: -40px; left: 70%;">
    <button id="updateBtn" type="button" class="btn btn-primary">更新</button>
    <button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
  </div>
  <hr style="position: relative; top: -40px;">
</div>
<form class="form-horizontal" role="form">

  <input id="id" type="hidden" value="${dicValue.id}">

  <div class="form-group">
    <label for="typeCode" class="col-sm-2 control-label">字典类型编码</label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="typeCode" style="width: 200%;" value="${dicValue.typeCode}"
             readonly>
    </div>
  </div>

  <div class="form-group">
    <label for="value" class="col-sm-2 control-label">字典值<span
            style="font-size: 15px; color: red;">*</span></label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="value" style="width: 200%;" value="${dicValue.value}">
      <span id="message"></span>
    </div>
  </div>

  <div class="form-group">
    <label for="text" class="col-sm-2 control-label">文本</label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="text" style="width: 200%;" value="${dicValue.text}">
    </div>
  </div>

  <div class="form-group">
    <label for="orderNo" class="col-sm-2 control-label">排序号</label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="orderNo" style="width: 200%;" value="${dicValue.orderNo}">
    </div>
  </div>
</form>

<div style="height: 200px;"></div>
</body>
</html>