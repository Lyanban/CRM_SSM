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
  <%--<link href="jquery/bs_pagination/jquery.bs_pagination.min.css" type="text/css">--%>

  <script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
  <script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
  <script type="text/javascript"
          src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
  <script type="text/javascript"
          src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

  <script src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
  <script src="jquery/bs_pagination/en.js"></script>

  <script type="text/javascript">
      $(function () {
          $(".time").datetimepicker({
              minView: "month",
              language: 'zh-CN',
              format: 'yyyy-mm-dd',
              autoclose: true,
              todayBtn: true,
              pickerPosition: "bottom-left"
          });

          getActivityList(1, 5);

          $("#checkAll").on("change", function () {
              $("input[name=check]").prop("checked", this.checked);
          });

          $("#tbody").on("change", $("input[name=check]"), function () {
              $("#checkAll").prop("checked", $("input[name=check]").length === $("input[name=check]:checked").length);
          });

          $("#editBtn").on("click", function () {
              let n = $("input[name=check]:checked").length;
              if (1 === n) {
                  let id = $("input[name=check]:checked").val();
                  $.get(
                      "workbench/activity/userListAndActivity/" + id,
                      function (resp) {
                          if (10000 === resp.success) {
                              let html = '<option value="-1"></option>';
                              $.each(resp.data.userList, function (index, user) {
                                  html += '<option value="' + user.id + '">' + user.name + '</option>';
                              });
                              // TODO: ?????????owner????????????????????????????????????UUID
                              $("#edit-owner").html(html).val(resp.data.activity.owner);
                              $("#edit-id").val(id);
                              $("#edit-name").val(resp.data.activity.name);
                              $("#edit-startDate").val(resp.data.activity.startDate);
                              $("#edit-endDate").val(resp.data.activity.endDate);
                              $("#edit-cost").val(resp.data.activity.cost);
                              $("#edit-description").val(resp.data.activity.description);
                              $("#editActivityModal").modal("show");
                          } else {
                              alert(resp.message);
                          }
                      }
                  );
              } else if (0 === n) {
                  alert("????????????????????????????????????");
              } else {
                  alert("?????????????????????????????????");
              }
          });

          $("#updateBtn").on("click", function () {
              let id = $("input[name=check]:checked").val();
              let owner = $.trim($("#edit-owner").val());
              let name = $.trim($("#edit-name").val());
              let startDate = $.trim($("#edit-startDate").val());
              let endDate = $.trim($("#edit-endDate").val());
              let cost = $.trim($("#edit-cost").val());
              let description = $.trim($("#edit-description").val());
              let editBy = "${sessionScope.user.name}";

              if ("-1" === owner) {
                  alert("?????????????????????");
                  $("#create-owner").focus();
                  return false;
              }
              if ("" === name) {
                  alert("????????????????????????");
                  $("#create-name").focus();
                  return false;
              }

              $.post(
                  "workbench/activity/update",
                  {
                      "id": id,
                      "owner": owner,
                      "name": name,
                      "startDate": startDate,
                      "endDate": endDate,
                      "cost": cost,
                      "description": description,
                      "editBy": editBy
                  },
                  function (resp) {
                      if (10000 === resp.success) {
                          getActivityList(1, 5);
                          $("#editActivityModal").modal("hide");
                      } else {
                          alert(resp.message);
                      }
                  }
              );
          });

          $("#edit-close-btn").on("click", function () {
              $("#editActivityModal").modal("hide");
          });

          $("#createBtn").on("click", function () {
              $.get(
                  "settings/user/list",
                  function (resp) {
                      if (10000 === resp.success) {
                          let html = '<option value="-1"></option>';
                          $.each(resp.userList, function (index, user) {
                              html += '<option value="' + user.id + '">' + user.name + '</option>';
                          });
                          $("#create-owner").html(html).val("${sessionScope.user.id}");
                          $("#createActivityModal").modal("show");
                      } else {
                          alert(resp.message);
                      }
                  }
              );
          });

          $("#saveBtn").on("click", function () {
              let owner = $.trim($("#create-owner").val());
              let name = $.trim($("#create-name").val());
              let startDate = $.trim($("#create-startDate").val());
              let endDate = $.trim($("#create-endDate").val());
              let cost = $.trim($("#create-cost").val());
              let description = $.trim($("#create-description").val());
              let createBy = "${sessionScope.user.name}";

              if ("-1" === owner) {
                  alert("?????????????????????");
                  $("#create-owner").focus();
                  return false;
              }
              if ("" === name) {
                  alert("????????????????????????");
                  $("#create-name").focus();
                  return false;
              }
              /*if ("" === startDate) {
                  alert("????????????????????????");
                  $("#create-startDate").focus();
                  return false;
              }
              if ("" === endDate) {
                  alert("????????????????????????");
                  $("#create-endDate").focus();
                  return false;
              }*/

              $.post(
                  "workbench/activity/save",
                  {
                      "owner": owner,
                      "name": name,
                      "startDate": startDate,
                      "endDate": endDate,
                      "cost": cost,
                      "description": description,
                      "createBy": createBy
                  },
                  function (resp) {
                      if (10000 === resp.success) {
                          getActivityList(1, 5);
                          $("#createActivityModal").modal("hide");
                          $("#create-owner").val("-1");
                          $("#create-name").val("");
                          $("#create-startDate").val("");
                          $("#create-endDate").val("");
                          $("#create-cost").val("");
                          $("#create-description").val("");
                      } else {
                          alert(resp.message);
                      }
                  }
              );
          });

          $("#create-close-btn").on("click", function () {
              $("#create-owner").val("-1");
              $("#create-name").val("");
              $("#create-startDate").val("");
              $("#create-endDate").val("");
              $("#create-cost").val("");
              $("#create-description").val("");
              $("#createActivityModal").modal("hide");
          });

          $("#deleteBtn").on("click", function () {
              let $checkedActivity = $("input[name=check]:checked");
              if (0 < $checkedActivity.length) {
                  let param = "";
                  let names = "";
                  $.each($checkedActivity, function (index, activity) {
                      param += "ids=" + activity.value + "&";
                      names += $("#n_" + activity.value).text() + "???";
                  });
                  param = param.substr(0, param.length - 1);
                  names = names.substr(0, names.length - 1);
                  if (confirm("??????????????????" + names + "?????????????????????")) {
                      $.post(
                          "workbench/activity/delete",
                          param,
                          function (resp) {
                              if (10000 === resp.success) {
                                  getActivityList(1, 5);
                              } else {
                                  alert(resp.message);
                              }
                          }
                      );
                  }
              } else {
                  alert("????????????????????????????????????");
              }
          });

          $("#searchBtn").on("click", function () {
              let name = $.trim($("#search-name").val());
              let owner = $.trim($("#search-owner").val());
              let startDate = $.trim($("#search-startDate").val());
              let endDate = $.trim($("#search-endDate").val());
              $("#hidden-name").val(name);
              $("#hidden-owner").val(owner);
              $("#hidden-startDate").val(startDate);
              $("#hidden-endDate").val(endDate);

              getActivityList(1, 5);
          });

          $("#importActivityBtn").on("click", function () {
              if (null === $("#activityFile").val() || "" === $("#activityFile").val()) {
                  alert("??????????????????");
                  return false;
              }
              $("#uploadForm").submit();
          });

          $("#exportAllActivityBtn").on("click", function () {
              if (confirm("??????????????????????????????????????????")) {
                  location.href = "workbench/activity/file/export/all";
              }
          });

          $("#exportSelectedActivityBtn").on("click", function () {
              let $selectedActivities = $("input[name=check]:checked");
              if (0 >= $selectedActivities.length) {
                  alert("????????????????????????????????????");
              }
              let param = "";
              let names = "";
              $.each($selectedActivities, function (index, activity) {
                  param += "activityId=" + activity.value + "&";
                  names += $("#n_" + activity.value).text() + "???";
              });
              param = param.substr(0, param.length - 1);
              names = names.substr(0, names.length - 1);
              if (confirm("??????????????????" + names + "?????????????????????")) {
                  location.href = "workbench/activity/file/export/select?" + param;
              }
          });
      });

      function getActivityList(pageNo, pageSize) {
          let name = $.trim($("#hidden-name").val());
          let owner = $.trim($("#hidden-owner").val());
          let startDate = $.trim($("#hidden-startDate").val());
          let endDate = $.trim($("#hidden-endDate").val());

          $.get(
              "workbench/activity/list",
              {
                  "pageNo": pageNo,
                  "pageSize": pageSize,
                  "name": name,
                  "owner": owner,
                  "startDate": startDate,
                  "endDate": endDate
              },
              function (resp) {
                  if (10000 === resp.success) {
                      let html = "";
                      $.each(resp.vo.dataList, function (index, item) {
                          if (index % 2 === 0) {
                              html += '<tr class="active">';
                          } else {
                              html += '<tr class="">';
                          }
                          html += '<td><input type="checkbox" name="check" value="' + item.id + '"/></td>';
                          html += '<td><a id="n_' + item.id + '" style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detail/' + item.id + '\'">'
                              + item.name + '</a></td>';
                          html += '<td>' + item.owner + '</td>';
                          html += '<td>' + item.startDate + '</td>';
                          html += '<td>' + item.endDate + '</td>';
                          html += '</tr>';
                      });
                      $("#tbody").html(html);
                      let totalPages = (resp.vo.total % pageSize === 0) ? (resp.vo.total / pageSize) : (Math.ceil(resp.vo.total / pageSize));
                      // console.log(resp.vo.total / pageSize);
                      //????????????????????????????????????????????????????????????????????????
                      $("#activityPage").bs_pagination({
                          currentPage: pageNo, // ??????
                          rowsPerPage: pageSize, // ???????????????????????????
                          maxRowsPerPage: 20, // ?????????????????????????????????
                          totalPages: totalPages, // ?????????
                          totalRows: resp.vo.total, // ???????????????

                          visiblePageLinks: 3, // ??????????????????

                          showGoToPage: true,
                          showRowsPerPage: true,
                          showRowsInfo: true,
                          showRowsDefaultInfo: true,

                          //????????????????????????????????????????????????????????????
                          onChangePage: function (event, data) {
                              getActivityList(data.currentPage, data.rowsPerPage);
                          }
                      });
                  } else {
                      $("#tbody").html(resp.message);
                  }
              }
          );
      }
  </script>
</head>
<body>

<!-- ????????????????????????????????? -->
<div class="modal fade" id="createActivityModal" role="dialog">
  <div class="modal-dialog" role="document" style="width: 85%;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">??</span>
        </button>
        <h4 class="modal-title" id="myModalLabel1">??????????????????</h4>
      </div>

      <div class="modal-body">
        <form class="form-horizontal" role="form">
          <div class="form-group">
            <label for="create-owner" class="col-sm-2 control-label">?????????<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <select class="form-control" id="create-owner">
              </select>
            </div>
            <label for="create-name" class="col-sm-2 control-label">??????<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="create-name">
            </div>
          </div>

          <div class="form-group">
            <label for="create-startDate" class="col-sm-2 control-label">????????????</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control time" id="create-startDate" placeholder="???????????????" readonly>
            </div>
            <label for="create-endDate" class="col-sm-2 control-label">????????????</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control time" id="create-endDate" placeholder="???????????????" readonly>
            </div>
          </div>

          <div class="form-group">
            <label for="create-cost" class="col-sm-2 control-label">??????</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="create-cost">
            </div>
          </div>

          <div class="form-group">
            <label for="create-description" class="col-sm-2 control-label">??????</label>
            <div class="col-sm-10" style="width: 81%;">
              <textarea class="form-control" rows="3" id="create-description"></textarea>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button id="create-close-btn" type="button" class="btn btn-default">??????</button>
        <button id="saveBtn" type="button" class="btn btn-primary">??????</button>
      </div>
    </div>
  </div>
</div>

<!-- ????????????????????????????????? -->
<div class="modal fade" id="editActivityModal" role="dialog">
  <div class="modal-dialog" role="document" style="width: 85%;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">??</span>
        </button>
        <h4 class="modal-title" id="myModalLabel2">??????????????????</h4>
      </div>
      <div class="modal-body">
        <form class="form-horizontal" role="form">
          <input type="hidden" id="edit-id">
          <div class="form-group">
            <label for="edit-owner" class="col-sm-2 control-label">?????????<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <select class="form-control" id="edit-owner">
              </select>
            </div>
            <label for="edit-name" class="col-sm-2 control-label">??????<span
                    style="font-size: 15px; color: red;">*</span></label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="edit-name" value="">
            </div>
          </div>

          <div class="form-group">
            <label for="edit-startDate" class="col-sm-2 control-label">????????????</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control time" id="edit-startDate" value="" readonly>
            </div>
            <label for="edit-endDate" class="col-sm-2 control-label">????????????</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control time" id="edit-endDate" value="" readonly>
            </div>
          </div>

          <div class="form-group">
            <label for="edit-cost" class="col-sm-2 control-label">??????</label>
            <div class="col-sm-10" style="width: 300px;">
              <input type="text" class="form-control" id="edit-cost" value="">
            </div>
          </div>

          <div class="form-group">
            <label for="edit-description" class="col-sm-2 control-label">??????</label>
            <div class="col-sm-10" style="width: 81%;">
              <textarea class="form-control" rows="3" id="edit-description"></textarea>
            </div>
          </div>
        </form>
      </div>
      <div class="modal-footer">
        <button id="edit-close-btn" type="button" class="btn btn-default">??????</button>
        <button id="updateBtn" type="button" class="btn btn-primary">??????</button>
      </div>
    </div>
  </div>
</div>

<!-- ????????????????????????????????? -->
<div class="modal fade" id="importActivityModal" role="dialog">
  <div class="modal-dialog" role="document" style="width: 85%;">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">
          <span aria-hidden="true">??</span>
        </button>
        <h4 class="modal-title" id="myModalLabel">??????????????????</h4>
      </div>
      <div class="modal-body" style="height: 350px;">
        <div style="position: relative;top: 20px; left: 50px;">
          ??????????????????????????????<small style="color: gray;">[?????????.xls???.xlsx??????]</small>
        </div>
        <div style="position: relative;top: 40px; left: 50px;">
          <form action="workbench/activity/file/upload" method="post" id="uploadForm" enctype="multipart/form-data">
            <input type="file" name="activityFile" id="activityFile">
          </form>
        </div>
        <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;">
          <h3>????????????</h3>
          <ul>
            <li>???????????????Excel????????????????????????XLS/XLSX????????????</li>
            <li>?????????????????????????????????????????????</li>
            <li>????????????????????????????????????5MB???</li>
            <li>?????????????????????????????????????????????yyyy-MM-dd?????????</li>
            <li>????????????????????????????????????????????????yyyy-MM-dd HH:mm:ss????????????</li>
            <li>?????????????????????????????????UTF-8 (?????????)????????????????????????????????????????????????????????????????????????</li>
            <li>??????????????????????????????????????????????????????????????????????????????</li>
          </ul>
        </div>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">??????</button>
        <button id="importActivityBtn" type="button" class="btn btn-primary">??????</button>
      </div>
    </div>
  </div>
</div>

<div>
  <div style="position: relative; left: 10px; top: -10px;">
    <div class="page-header">
      <h3>??????????????????</h3>
    </div>
  </div>
</div>

<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
  <div style="width: 100%; position: absolute;top: 5px; left: 10px;">

    <input type="hidden" id="hidden-name"/>
    <input type="hidden" id="hidden-owner"/>
    <input type="hidden" id="hidden-startDate"/>
    <input type="hidden" id="hidden-endDate"/>

    <%--?????????--%>
    <div class="btn-toolbar" role="toolbar" style="height: 80px;">
      <form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">??????</div>
            <input class="form-control" type="text" id="search-name" autocomplete="off">
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">?????????</div>
            <input class="form-control" type="text" id="search-owner" autocomplete="off">
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">????????????</div>
            <input class="form-control time" type="text" id="search-startDate" placeholder="????????????" autocomplete="off"
                   readonly>
          </div>
        </div>

        <div class="form-group">
          <div class="input-group">
            <div class="input-group-addon">????????????</div>
            <input class="form-control time" type="text" id="search-endDate" placeholder="????????????" autocomplete="off"
                   readonly>
          </div>
        </div>

        <button id="searchBtn" type="button" class="btn btn-default">??????</button>
        <button type="reset" class="btn btn-default">??????</button>
      </form>
    </div>

    <%--?????????--%>
    <div class="btn-toolbar" role="toolbar"
         style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
      <div class="btn-group" style="position: relative; top: 18%;">
        <button id="createBtn" type="button" class="btn btn-primary">
          <span class="glyphicon glyphicon-plus"></span> ??????
        </button>
        <button id="editBtn" type="button" class="btn btn-default">
          <span class="glyphicon glyphicon-pencil"></span> ??????
        </button>
        <button id="deleteBtn" type="button" class="btn btn-danger">
          <span class="glyphicon glyphicon-minus"></span> ??????
        </button>
      </div>
      <div class="btn-group" style="position: relative; top: 18%;">
        <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal">
          <span class="glyphicon glyphicon-import"></span> ??????????????????????????????
        </button>
        <button id="exportAllActivityBtn" type="button" class="btn btn-default">
          <span class="glyphicon glyphicon-export"></span> ????????????????????????????????????
        </button>
        <button id="exportSelectedActivityBtn" type="button" class="btn btn-default">
          <span class="glyphicon glyphicon-export"></span> ????????????????????????????????????
        </button>
      </div>
    </div>

    <div style="position: relative;top: 10px;">
      <table class="table table-hover">
        <thead>
        <tr style="color: #B3B3B3;">
          <td><input id="checkAll" type="checkbox"/></td>
          <td>??????</td>
          <td>?????????</td>
          <td>????????????</td>
          <td>????????????</td>
        </tr>
        </thead>
        <tbody id="tbody">
        </tbody>
      </table>
    </div>

    <div style="height: 50px; position: relative;top: 30px;">
      <div id="activityPage"></div>
    </div>
  </div>
</div>
</body>
</html>