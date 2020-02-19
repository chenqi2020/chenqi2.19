<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<!DOCTYPE html>
<html>
<head>
<title>Insert title here</title>
</head>
<body>
	<form action="list">
	查询:<input type="text" name="did">
	<input type="button" value="查询">
	
	</form>
	<table class="table table-bordered table-hover">
		<tr align="center">
		<td>编号</td>
		<td>文本</td>
		<td>地址</td>
		<td>用户</td>
		<td><a href="javascript:myOrder('created')">举报时间</a></td>
		<td>操作</td>
		</tr>
	<c:forEach items="${info.list}" var="u">
	<tr align="center">
	<td>${u.did}</td>
	<td>${u.text}</td>
	<td>${u.url}</td>
	<td>${u.user_id}</td>
	<td>${u.created}</td>
	<td>
	<a href="admin/article/dele?did=${u.did}">删除</a>
	</td>
	</tr>
	</c:forEach>
	
	<tr align="center">
			<td colspan="10"><jsp:include
					page="/WEB-INF/view/common/pages.jsp" /></td>

		</tr>
	</table>
	
</body>
<script type="text/javascript">
//排序
function myOrder(orderName){
	 
	 var orderMethod ='${complainVO.orderMethod=="desc"?"asc":"desc"}';
	var url="/admin/article/collect?orderName="+orderName+"&orderMethod="+orderMethod 
	 $("#center").load(url);
}
</script>
</html>