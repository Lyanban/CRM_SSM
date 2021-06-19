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
          $("input[name=loginAct]").focus();

          $(window).keydown(function (event) {
              if (13 === event.keyCode) {
                  login();
              }
          });

          $("#loginBtn").on("click", login);
      });

      function login() {
          let loginAct = $("input[name=loginAct]").val();
          let loginPwd = $("input[name=loginPwd]").val();
          if ("" === loginAct || "" === loginPwd) {
              $("#message").text("用户名或密码不能为空");
              return false;
          } else {
              $.post(
                  "settings/user/login.do",
                  {
                      "loginAct": loginAct,
                      "loginPwd": loginPwd
                  },
                  function (resp) {
                      if (10000 === resp.success) {
                          window.location.href = "workbench/index";
                      } else {
                          $("#message").text(resp.message);
                      }
                  }
              );
              /*$.ajax({
                  url: "settings/user/login.do",
                  data: {
                      "loginAct": loginAct,
                      "loginPwd": loginPwd
                  },
                  type: "post",
                  dataType: "json",
                  success: function(resp) {
                      alert(resp.success);
                      if (10000 === resp.success) {
                          // window.location.href = "workbench/index.jsp";
                      } else {
                          $("#message").text(resp.message);
                      }
                  }
              });*/
          }
      }
  </script>
</head>
<body>
<div style="position: absolute; top: 0; left: 0; width: 60%;">
  <img src="image/IMG_7114.JPG" style="width: 100%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
  <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">
    CRM &nbsp;<span style="font-size: 12px;">&copy;2021&nbsp;LyanbA</span>
  </div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
  <div style="position: absolute; top: 0px; right: 60px;">
    <div class="page-header">
      <h1>登录</h1>
    </div>
    <form  class="form-horizontal" role="form">
      <div class="form-group form-group-lg">
        <div style="width: 350px;">
          <input class="form-control" name="loginAct" type="text" placeholder="用户名">
        </div>
        <div style="width: 350px; position: relative;top: 20px;">
          <input class="form-control" name="loginPwd" type="password" placeholder="密码">
        </div>
        <div class="checkbox" style="position: relative;top: 30px; left: 10px;">
          <label>
            <input type="checkbox"> 十天内免登录
          </label>
          &nbsp;&nbsp;
          <span id="message" style="color: red"></span>
        </div>
        <button id="loginBtn" type="button" class="btn btn-primary btn-lg btn-block"
                style="width: 350px; position: relative;top: 45px;">登录
        </button>
      </div>
    </form>
  </div>
</div>
</body>
</html>