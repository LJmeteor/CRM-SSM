<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	// 设置动态初始访问路径（这里本地是http://127.0.0.1:8080/crm/）
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function () {
		// 给浏览器窗口添加键盘按下事件（主要目的：回车键执行登录）
		$(window).keydown(function (event) {
			// 实现功能：按下回车键，则提交登录请求（回车键对应的值为13）
			if (event.keyCode == 13) {
				$("#loginBtn").click(); // 通过该代码自动执行登录单击事件
			}
		});
		// 给登录按钮添加单机事件
		$("#loginBtn").click(function () {
			//collect data
			var loginAct = $.trim($("#loginAct").val());
			var loginPwd = $.trim($("#loginPwd").val());
			var isRemPwd = $("#isRemPwd").prop("checked");
			if (loginAct == "") {
				alert("UserName cannot be empty!")
				return
			}
			if (loginPwd == "") {
				alert("Password cannot be empty!")
				return
			}
			// waiting!
			// $("#msg").html("loading....... please wait");
			// send async request
			$.ajax({
				url: 'settings/qx/user/login.do',
				data: {
					loginAct: loginAct,
					loginPwd: loginPwd,
					idRemPwd: isRemPwd
				},
				type: 'post',
				dataType: 'json',
				success: function (data) {
					if (data.code == "1") {
						window.location.href = "workbench/index.do";
					} else {
						// login fauilre
						$("#msg").html(data.message);
					}
				},
				beforeSend:function (){// before ajax sending request
				                       // True: ajax continues to send request False: will never send request
					$("#msg").html("loading....... please wait");
					return true;
				}
			});
			// 发送请求

		});

		// 给注册按钮添加单击事件

	});
</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2022 ylx</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input id="loginAct" class="form-control" type="text" value="${cookie.loginAct.value}" placeholder="用户名">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input id="loginPwd" class="form-control" type="password" value="${cookie.loginPwd.value}" placeholder="密码">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
                            <%--存在账号密码cookie就默认选择复选框，如果不存在那么默认不选中--%>
							<c:if test="${not empty cookie.loginAct and not empty cookie.loginPwd}">
								<input id="isRemPwd" type="checkbox" checked>
							</c:if>
							<c:if test="${empty cookie.loginAct and empty cookie.loginPwd}">
								<input id="isRemPwd" type="checkbox">
							</c:if>
							记住密码
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color: red"></span>
					</div>
					<button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
					<br />
					<button type="button" class="btn btn-primary btn-lg btn-block" id="registerBtn" style="width: 350px; position: relative;top: 45px;">注册</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>