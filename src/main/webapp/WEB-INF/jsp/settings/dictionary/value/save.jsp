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
          getDicTypeCodeList();

          let $typeCode = $("#typeCode");

          $typeCode.focus();

          $typeCode.on("blur", function () {
              if (this.value === "-1") {
                  $("#message1").text("请选择字典类型编码！");
                  return false;
              }
          });

          $("#value").on("blur", function () {
              if (this.value === "") {
                  $("#message2").text("请输入字典值！");
                  return false;
              }
          });

          $typeCode.on("change", function () {
              $("#message1").text("");
          });

          $("#value").on("keyup", function () {
              $("#message2").text("");
          });

          $("#saveBtn").on("click", function () {
              let typeCode = $.trim($typeCode.val());
              let value = $.trim($("#value").val());
              let text = $.trim($("#text").val());
              let orderNo = $.trim($("#orderNo").val());

              if (typeCode === "-1") {
                  $("#message1").text("请选择字典类型编码！");
                  return false;
              }

              $("#message1").text("");

              if (value === "") {
                  $("#message2").text("请输入字典值！");
                  return false;
              }

              $("#message2").text("");

              $.post(
                  "settings/dictionary/value/save",
                  {
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
          });
      });

      function getDicTypeCodeList() {
          $.get(
              "settings/dictionary/type/list",
              function (resp) {
                  let html = "<option value='-1'>请选择字典类型编码</option>";
                  $.each(resp, function (index, item) {
                      html += '<option value="' + item.code + '">' + item.name + '</option>';
                  });
                  $("#typeCode").html(html);
              }
          );
      }
  </script>
</head>
<body>

<div style="position:  relative; left: 30px;">
  <h3>新增字典值</h3>
  <div style="position: relative; top: -40px; left: 70%;">
    <button id="saveBtn" type="button" class="btn btn-primary">保存</button>
    <button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
  </div>
  <hr style="position: relative; top: -40px;">
</div>
<form class="form-horizontal" role="form">

  <div class="form-group">
    <label for="typeCode" class="col-sm-2 control-label">字典类型编码<span
            style="font-size: 15px; color: red;">*</span></label>
    <div class="col-sm-10" style="width: 300px;">
      <select class="form-control" id="typeCode" style="width: 200%;">
        <%--<option></option>
        <option>性别</option>
        <option>机构类型</option>--%>
      </select>
      <span id="message1" style="color: red"></span>
    </div>
  </div>

  <div class="form-group">
    <label for="value" class="col-sm-2 control-label">字典值<span
            style="font-size: 15px; color: red;">*</span></label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="value" style="width: 200%;" placeholder="请输入字典值">
      <span id="message2" style="color: red"></span>
    </div>
  </div>

  <div class="form-group">
    <label for="text" class="col-sm-2 control-label">文本</label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="text" style="width: 200%;">
    </div>
  </div>

  <div class="form-group">
    <label for="orderNo" class="col-sm-2 control-label">排序号</label>
    <div class="col-sm-10" style="width: 300px;">
      <input type="text" class="form-control" id="orderNo" style="width: 200%;">
    </div>
  </div>
</form>

<div style="height: 200px;"></div>
</body>
</html>