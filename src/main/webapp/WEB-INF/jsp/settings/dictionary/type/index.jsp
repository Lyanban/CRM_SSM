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

  <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
  <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
  <script>
      $(function () {
          $("#checkAll").on("change", function () {
              $("input[name=check]").prop("checked", this.checked);
          });

          $("input[name=check]").on("change", function () {
              $("#checkAll").prop("checked", $("input[name=check]").length === $("input[name=check]:checked").length);
          });
      });
  </script>
</head>
<body>

<div>
  <div style="position: relative; left: 30px; top: -10px;">
    <div class="page-header">
      <h3>字典类型列表</h3>
    </div>
  </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
  <div class="btn-group" style="position: relative; top: 18%;">
    <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/type/toSave'">
      <span class="glyphicon glyphicon-plus"></span> 创建
    </button>
    <button type="button" class="btn btn-default" onclick="window.location.href='settings/dictionary/type/toEdit'">
      <span class="glyphicon glyphicon-edit"></span> 编辑
    </button>
    <button type="button" class="btn btn-danger">
      <span class="glyphicon glyphicon-minus"></span> 删除
    </button>
  </div>
</div>
<div style="position: relative; left: 30px; top: 20px;">
  <table class="table table-hover">
    <thead>
    <tr style="color: #B3B3B3;">
      <td><input type="checkbox" id="checkAll"/></td>
      <td>序号</td>
      <td>编码</td>
      <td>名称</td>
      <%--<td>描述</td>--%>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dicTypeList}" var="dicType" varStatus="status">
    <tr class="${status.count % 2 == 0 ? "" : "active"}">
      <td><input type="checkbox" name="check"/></td>
      <td>${status.count}</td>
      <td>${dicType.code}</td>
      <td>${dicType.name}</td>
        <%--<td>${dicType.description}</td>--%>
      </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>