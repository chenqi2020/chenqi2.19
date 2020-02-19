<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>举报</title>
<!-- 引入样式 -->
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
</head>
<script type="text/javascript">



function add(){
	var formData = new FormData($( "#form2" )[0]);
	$.ajax({
		type:"post",
		url:"/colled",
		data:formData,
		processData : false,
		// 告诉jQuery不要去设置Content-Type请求头
		contentType : false,
		success:function(flag){
			if(flag){
				alert("举报成功")
				location.href="/";
			}else{
				alert("举报失败,url 不正确")
			}
		}
	})
}
</script>
<body>
	<div align="center" class="container" style="width: 600px">
		<h1>收藏信息</h1>
		<hr>
		<form  id="form2" method="post">
		 <input type="hidden" name="user_id" value="${article.id }">
			
			<div class="form-group form-inline">
				收藏内容：
				<textarea rows="10" cols="100" name="text" class="form-control"></textarea>
			</div>
			<div class="form-group form-inline">
				收藏地址：<input type="text" name="url" class="form-control">
			</div>
			
			
			<div class="form-group form-inline">
				<button type="button" onclick="add()">举报</button>
			</div>
		</form>
	</div>
	
</body>
</html>