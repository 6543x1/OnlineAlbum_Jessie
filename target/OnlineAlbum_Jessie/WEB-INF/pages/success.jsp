<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/2
  Time: 11:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>成功</title>
</head>
<body>
<h3>成功</h3>
<br/>
<a href="${pageContext.request.contextPath}/user/isLogin">检查登录状态</a>
<br/>
<a href="${pageContext.request.contextPath}/user/testRedirect">回到首页</a>
<br/>
<h3>当前Session中用户</h3>
${ username }
userfid=${userfid}
</body>
</html>
