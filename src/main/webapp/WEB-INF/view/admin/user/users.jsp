<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<title>用户列表</title>
</head>
<body>
<div class="container-fluid">
	<div style="margin-bottom: 10px;" class="form-inline">
		<label for="username">用户名:</label>
		<input id="username" type="text" name="username" value="${username}" class="form-control">&nbsp;
		<button type="button" onclick="query()" class="btn btn-info">查询</button>
	</div>
<table class="table table-dark table-bordered table-hover">
  <tr align="center">
    <th>序号</th>
    <th>用户名</th>
    <th>昵称</th>
    <th>生日</th>
    <th>注册时间</th>
    <th>操作</th>
  </tr>
  <c:forEach items="${info.list}" var="user" varStatus="i">
  <tr align="center">
    <td>${i.count}</td>
    <td>${user.username}</td>
    <td>${user.nickname}</td>
    <td><fmt:formatDate value="${user.birthday}" pattern="yyyy-MM-dd"/></td>
    <td><fmt:formatDate value="${user.created}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
    <td>
    	<c:if test="${user.locked==0}">
    		<button type="button" class="btn btn-success" onclick="update(${user.id},this)">正常</button>
    	</c:if>
    	<c:if test="${user.locked==1}">
    		<button type="button" class="btn btn-warning" onclick="update(${user.id},this)">停用</button>
    	</c:if>
    </td>
  </tr>
  </c:forEach>
  <tr>
	<td colspan="100">
		<jsp:include page="/WEB-INF/view/common/pages.jsp"/>
		</td>
	</tr>
</table>
</div>
</body>
<script type="text/javascript">
	function goPage(page){
		var url="/admin/user/selects?page="+page 
		$("#center").load(url);	
	}
	
	function query(){
		var url="/admin/user/selects?username="+$("[name='username']").val();
		$("#center").load(url);	
	}
	
	// 更新用户的状态locked  1:停用  0:正常
	function update(id,obj){
		// 要改变为的状态
		var locked = $(obj).text()=="正常"?1:0;
		$.post("/admin/user/update",{id:id,locked:locked},function(flag){
			if(flag){
				alert("操作成功!")
				// 改变内容
				$(obj).text(locked==1?"停用":"正常");
				// 改变颜色
				$(obj).attr("class",locked==1?"btn btn-warning":"btn btn-success")
			}else{
				alert("操作失败!")
			}
		});
	}
</script>
</html>