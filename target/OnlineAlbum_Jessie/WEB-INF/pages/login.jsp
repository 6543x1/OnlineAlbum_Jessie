<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/2
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>登入</title>
</head>
<body>
<form id="login" enctype="text/plain">
    <table>
        <tr>
            <td>用户名：<input type="text" name="username"></td>
        </tr>
        <tr>
            <td>密码：<input type="password" name="password"></td>
        </tr>
        <!--这里的name一定要和controller中对应方法参数名一样，否则会NullPointerException -->
        <tr>
            <td><input type="button" value="登录" onclick="login()"></td>
        </tr>
    </table>
</form>
<a href="${pageContext.request.contextPath}/index.jsp">返回首页</a>
<form id="register" enctype="text/plain">
    <table>
        注册用户<br/>
        <tr>
            <td>请输入用户名：<input type="text" name="username"></td>
        </tr>
        <tr>
            <td>请输入密码：<input type="password" name="password"></td>
        </tr>
        <!--这里的name一定要和controller中对应方法参数名一样，否则会NullPointerException -->
        <tr>
            <td><input type="button" value="注册" onclick="register()"></td>
        </tr>
    </table>
</form>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script>
    function login() {
        var user = new FormData(document.getElementById("login"));
        $.ajax({
            url: "${pageContext.request.contextPath}/user/login",
            type: "post",
            data: user,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data === "loginSuccess") {
                    alert("登录成功")
                    next()
                } else {
                    alert(data)
                }
            }
        });
    }

    function register() {
        var user = new FormData(document.getElementById("register"));
        $.ajax({
            url: "${pageContext.request.contextPath}/user/Register",
            type: "post",
            data: user,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data === "RegisterSuccess") {
                    alert("注册成功，请手动登录")

                } else {
                    alert(data)
                }
            }
        });
    }

    function next() {

        window.location = "${pageContext.request.contextPath}/index.jsp";

    }
</script>
</body>
</html>
