<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<script type="text/javascript" src="/resource/js/jquery-3.2.1.js"></script>
<link href="/resource/css/bootstrap.css" rel="stylesheet">
<title>文章列表</title>
</head>
<body>
	<div class="container-fluid">
	<div style="margin-bottom: 10px;" class="form-inline">
		<label for="title">标题:</label>
		<input id="title" type="text" name="title" value="${article.title}" class="form-control">
		&nbsp;
		<select class="form-control" name="status" id="status">
			<option value="0">待审</option>
			<option value="1">以审</option>
			<option value="-1">驳回</option>
			<option value="99">全部</option>
		</select>&nbsp;
		<button type="button" onclick="query()" class="btn btn-info">查询</button>
	</div>
<table class="table table-dark table-bordered table-hover">
  <tr align="center">
    <th>序号</th>
    <th>文章标题</th>
    <th>栏目</th>
    <th>分类</th>
    <th>作者</th>
    <th>文章状态</th>
    <th>发布时间</th>
    <th colspan="2">操作</th>
  </tr>
  <c:forEach items="${info.list}" var="article" varStatus="i">
  <tr align="center">
    <td>${i.count}</td>
    <td>${article.title}</td>
    <td>${article.channel.name}</td>
    <td>${article.category.name}</td>
    <td>${article.user.username}</td>
    <td>${article.status==0?'待审':article.status==1?'以审':'驳回'}</td>
    <td><fmt:formatDate value="${article.created}" pattern="yyyy-MM-dd HH:mm:ss"/> </td>
    <td>
    	<c:if test="${article.hot==0}">
    		<button type="button" class="btn btn-success" onclick="update(${article.id},this)">非热门</button>
    	</c:if>
    	<c:if test="${article.hot==1}">
    		<button type="button" class="btn btn-warning" onclick="update(${article.id},this)">热门</button>
    	</c:if>
    </td>
    <td>
    	<a href="/admin/article/select?id=${article.id}" target="_blank">详情</a>
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
	// 分页
	function goPage(page){
		var url="/admin/article/selects?page="+page+"&title="+$("[name='title']").val()+"&status="+$("[name='status']").val();
		$("#center").load(url);	
	}
	
	function query(){
		 var  url="/admin/article/selects?title="+$("[name='title']").val()+"&status="+$("[name='status']").val();
		 $("#center").load(url)
	 }
	
	// 让下拉框查询条件回显
	$(function(){
		$("#status").val('${article.status}');
	})
	
	// 更新文章热门状态locked  1:停用  0:正常
	function update(id,obj){
		// 要改变为的状态
		var hot = $(obj).text()=="非热门"?1:0;
		$.post("/admin/article/update",{id:id,hot:hot},function(flag){
			if(flag){
				alert("操作成功!")
				// 改变内容
				$(obj).text(hot==1?"热门":"非热门");
				// 改变颜色
				$(obj).attr("class",hot==1?"btn btn-warning":"btn btn-success")
			}else{
				alert("操作失败!")
			}
		});
	}
</script>
</html>