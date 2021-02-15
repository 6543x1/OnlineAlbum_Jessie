<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/1
  Time: 15:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>相册——首页</title>
    <script src="js/jquery.min.js"></script>
    <script>
        $(function (){
            $("#btn").click(function (){
                //alert("HELLO");
                $.ajax({
                    //编写json格式
                    url:"user/login",
                    contentType:"application/json;charset=UTF-8",
                    data:'{"username":"myUser","password":"123456"}',
                    dateType:"json",
                    type:"post",
                    success:function(data){

                }
                });
            });

        });
        $(function (){
            $("#delSession").click(function (){
                $.ajax({
                    //编写json格式
                    url:"user/delSessionAttribute",
                });
                //上面是真的执行了
                alert("已执行");
            });
        });
        
    </script>
</head>
<body>
    <h3>欢迎当前用户(没显示东西先登录哈）:${username}</h3>
    <br/>
    <h3>SpringMVC文件上传测试</h3>
    <form action="Image/fileupload1" method="post" enctype="multipart/form-data">
        选择文件:<input type="file" accept="image/*" name="upload"/><br/>
        <input type="submit" value="暂时不用的按钮">
    </form>
    <br/>
    <a href="user/tologin">登录账号</a>
    <a href="user/testRedirect">测试重定向到本页</a>
    <br/>
    <a href="user/ToUpload">上传文件（请先登录）</a>
    <br/>
    <button id="btn">发送请求</button>
    <br/>
    <a href="user/getSessionAttribute">检查Session的状态(检查登录)</a>
    <br/>
    <button id="delSession">恢复session到初始状态(退出登录)</button>
        <br/>
    <a href="Image/download?id=4">下载示例图片</a>
    <br/>
    <a href="Image/getUserFolders?father=${userfid}">查看当前用户的文件目录</a>
    <br/>
    <h3>下面会显示一张图片（好像生成缩略图挺麻烦只能弄原图）</h3>
    <img src="Image/showImage?id=3" width="400px" height="300px">
</body>
</html>
