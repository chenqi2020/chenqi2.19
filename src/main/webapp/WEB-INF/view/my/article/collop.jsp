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
	<div>
	    <dl>
	      <c:forEach items="${info.lists}" var="collect">
	       <dt style="font-size: 20px"></dt>
	       	 <dt>${collect.user_id },<fmt:formatDate value="${collect.created }" pattern="yyyy-MM-dd HH:mm:ss"/>  </dt>
	       
	      <hr>
	      </c:forEach>
	   </dl>
	    
	  
	  </div>
</body>
</html>