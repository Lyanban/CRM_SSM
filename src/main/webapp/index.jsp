<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<html>
<head>
  <base href="<%=basePath%>">
  <meta charset="UTF-8">
</head>
<body>
<script type="text/javascript">
    document.location.href = "settings/user/toLogin.do";
</script>
</body>
</html>