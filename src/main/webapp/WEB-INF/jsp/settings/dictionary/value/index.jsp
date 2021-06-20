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
</head>
<body>

<div>
  <div style="position: relative; left: 30px; top: -10px;">
    <div class="page-header">
      <h3>字典值列表</h3>
    </div>
  </div>
</div>
<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
  <div class="btn-group" style="position: relative; top: 18%;">
    <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/value/toSave'">
      <span class="glyphicon glyphicon-plus"></span> 创建
    </button>
    <button type="button" class="btn btn-default" onclick="window.location.href='settings/dictionary/value/toEdit'">
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
      <td><input type="checkbox"/></td>
      <td>序号</td>
      <td>字典值</td>
      <td>文本</td>
      <td>排序号</td>
      <td>字典类型编码</td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${dicValueList}" var="dicValue" varStatus="status">
    <tr class="${status.count % 2 == 0 ? "" : "active"}">
      <td><input type="checkbox"/></td>
      <td>${status.count}</td>
      <td>${dicValue.value}</td>
      <td>${dicValue.text}</td>
      <td>${dicValue.orderNo}</td>
      <td>${dicValue.typeCode}</td>
      </c:forEach>
    </tbody>
  </table>
</div>

</body>
</html>