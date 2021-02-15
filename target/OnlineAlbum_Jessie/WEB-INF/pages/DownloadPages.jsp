<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/12
  Time: 20:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>下载列表</h1><hr>
<c:forEach items="${ folderList }" var="folder">
    <h2>文件夹名：${folder.folderName }</h2><br>
    绝对路径：${folder.path }<br>
    FID：${folder.fid }<br>
    上一个目录fid：${folder.father }<br>
    <a href="${pageContext.request.contextPath}/Image/getUserFolders?father=${folder.father}">原地tp</a>
    <a href="${pageContext.request.contextPath}/Image/getUserFolders?father=${folder.fid}">进入下一个目录</a>
    <hr>
</c:forEach>
<c:forEach items="${ imageList }" var="image">
    <h2>图片名：${image.name }</h2><br>
    绝对路径：${image.path }<br>
    所属文件夹的FID：${image.fid }<br>
    上传时间：${image.uploadTime }<br>
    <a href="${pageContext.request.contextPath}/Image/download?id=${image.imageid}">下载这张图片</a>
    <button id="downloadImage" value="下载" onclick="downloadImage(${image.imageid})">下载（这个用不了）</button>
    <hr>
</c:forEach>
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script>
    function downloadImage(id){
        $.ajax({
            url: "${pageContext.request.contextPath}/Image/download",
            type: "post",
            data: id,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data === "success") {
                    alert("下载成功")
                } else {
                    alert(data)
                }
            }
        });
    }
</script>
</body>
</html>
