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
          $("#code").on("blur", function () {
              let code = $.trim($("#code").val());
              if ("" === code) $("#message").prop("style", "color: red").text("字典类型编码不能为空！");
          });

          $("#code").on("keyup", function () {
              let code = $.trim($("#code").val());
              if ("" === code) $("#message").text("");
              if (code.length > 0) {
                  $.get(
                      "settings/dictionary/type/getDicTypeByCode",
                      {"code": code},
                      function (resp) {
                          if (10000 === resp.success) {
                              $("#message").prop("style", "color: green").text(resp.message);
                          } else {
                              $("#message").prop("style", "color: red").text(resp.message);
                          }
                      }
                  );
              }
          });

          $("#saveBtn").on("click", function () {
              if ("√" === $("#message").text()) {
                  $.post(
                      "settings/dictionary/type/save",
                      {
                          "code": $.trim($("#code").val()),
                          "name": $.trim($("#name").val()),
                          "description": $.trim($("#description").val())
                      },
                      function (resp) {
                          if (10000 === resp.success) {
                              window.location.href = "settings/dictionary/index";
                          } else {
                              alert(resp.message);
                              return false;
                          }
                      }
                  );
              }
          });
      });
  </script>
</head>
<body>

<div style="position:  relative; left: 30px;">
  <h3>新增字典类型</h3>
  <div style="position: relative; top: -40px; left: 70%;">
    <button id="saveBtn" type="button" class="btn btn-primary">保存</button>
    <button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
  </div>
  <hr style="position: relative; top: -40px;">
</div>
<form class="form-horizontal" role="form">

  <div class="form-group">
    <label for="code" class="col-sm-2 control-label">编码<span
            style="font-size: 15px; color: red;">*</span></label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="code" style="width: 200%;">
      <span id="message"></span>
    </div>
  </div>

  <div class="form-group">
    <label for="name" class="col-sm-2 control-label">名称</label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="name" style="width: 200%;">
    </div>
  </div>

  <div class="form-group">
    <label for="description" class="col-sm-2 control-label">描述</label>
    <div class="col-sm-10" style="width: 300px;">
      <textarea class="form-control" rows="3" id="description" style="width: 200%;"></textarea>
    </div>
  </div>
</form>

<div style="height: 200px;"></div>
</body>
</html>