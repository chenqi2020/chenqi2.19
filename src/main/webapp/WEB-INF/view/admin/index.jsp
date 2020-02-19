<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<title>CMS后台管理系统</title>
</head>
<body>
<div class="container">
	<!-- header -->
	<div class="row" style="height: 80px;margin-top: 20px">
		<div class="col-md-12" style="background-color: skyblue">
			<img alt="" src="/resource/images/logo.png" style="height: 80px;" class="rounded-circle">
			<font color="red">CMS后台管理</font>
			<span>
				<font color="white"> 登录人:${sessionScope.admin.username }</font>
				 <a href="/passport/logout">注销</a>
			</span>
			
		</div>
	</div>
	<hr style="height: 2px;border: none;border-top: 2px dotted #185598;">
	<div class="row" style="height: 550px;margin-top: 20px">
		<!-- 中间区域 -->
		<div class="col-md-3" style="background-color: #ccc">
			<div style="margin-top: 18px;">
				<!-- As a link -->
				<nav class="navbar navbar-light bg-light">
	 				 <a class="navbar-brand" href="#" data="/admin/user/selects">用户管理</a>
				</nav>
				<nav class="navbar navbar-light bg-light" style="margin-top: 10px;">
	 				 <a class="navbar-brand" href="#" data="/admin/article/selects">文章管理</a>
				</nav>
				<nav class="navbar navbar-light bg-light" style="margin-top: 10px;">
	 				 <a class="navbar-brand" href="#" data="/admin/article/complains">举报管理</a>
				</nav>
				<nav class="navbar navbar-light bg-light" style="margin-top: 10px;">
	 				 <a class="navbar-brand" href="#" data="/admin/article/collect">收藏管理</a>
				</nav>
				<nav class="navbar navbar-light bg-light" style="margin-top: 10px;">
	 				 <a class="navbar-brand" href="#">栏目管理</a>
				</nav>
				<nav class="navbar navbar-light bg-light" style="margin-top: 10px;">
	 				 <a class="navbar-brand" href="#">分类管理</a>
				</nav>
				<nav class="navbar navbar-light bg-light" style="margin-top: 10px;">
	 				 <a class="navbar-brand" href="#">系统管理</a>
				</nav>
			</div>
		</div>
		<!-- 中间内容区域 -->
		<div class="col-md-9" id="center"></div>
	</div>
</div>
</body>
<script type="text/javascript">
	$(function () {
		
		// 默认显示用户列表
		$("#center").load("/admin/user/selects");
		
		/* 为a标签添加点击事件 */
		$("a").click(function () {
			 var url = $(this).attr("data");
			 $("a").removeClass("list-group-item-info");
			 // 为点击的a标签添加点击样式
			 $(this).addClass("list-group-item-info");
			 // 在中间区域加载url
			 $("#center").load(url);
		})	
	})
</script>
</html>