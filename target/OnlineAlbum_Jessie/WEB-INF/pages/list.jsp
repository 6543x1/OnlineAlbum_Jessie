<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/5
  Time: 21:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>LIST</title>
</head>
<body>
<h3>查询所有帐号</h3>
<c:forEach items="${list}" var="USER">
    ${USER.username}
</c:forEach>
</body>
</html>
