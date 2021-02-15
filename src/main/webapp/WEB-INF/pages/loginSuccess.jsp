<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/2
  Time: 17:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>登入成功</title>
</head>
<body>
<h3>成功</h3>
<h3>当前用户</h3>
<br/>
<a href="${pageContext.request.contextPath}/user/testRedirect">返回首页</a>
${ requestScope }
<br/>

</body>
</html>
