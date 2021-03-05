<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/3
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>图片上传</title>
</head>
<body>
<h3>当前登录用户:${ username }</h3>
<form id="tf" enctype="multipart/form-data">
    <h3>上传到相应的相册中，不过只能查找用户根目录的相册，不存在会自动创建新相册</h3>
    <table>
        <tr>
            <td>album<input type="text" name="album"></td>
        </tr>
        <tr>
            <td>文件上传<input type="file" accept="image/*" name="upload"></td>
        </tr>
        <!--这里的name一定要和controller中对应方法参数名一样，否则会NullPointerException -->
        <tr>
            <td><input type="button" value="上传" onclick="test()"></td>
        </tr>
    </table>
</form>
<br/>
<form id="uploadById" enctype="multipart/form-data">
    <table>
        <tr>
            <td>文件夹fid<input type="text" name="fid"></td>
        </tr>
        <tr>
            <td>文件上传<input type="file" accept="image/*" name="upload"></td>
        </tr>
        <!--这里的name一定要和controller中对应方法参数名一样，否则会NullPointerException -->
        <tr>
            <td><input type="button" value="上传" onclick="upload2()"></td>
        </tr>
    </table>
</form>
<br/>
<h3>创建文件夹</h3>
<form id="createFolder" enctype="multipart/form-data">
    <table>
        <tr>
            <td>父文件夹fid<input type="text" name="father"></td>
        </tr>
        <tr>
            <td>文件夹名字<input type="text" name="foldName"></td>
        </tr>
        <!--这里的name一定要和controller中对应方法参数名一样，否则会NullPointerException -->
        <tr>
            <td><input type="button" value="创建" onclick="createFolder()"></td>
        </tr>
    </table>
</form>
<script src="js/jquery.min.js"></script>
<script>
    function test() {
        alert("开始上传")
        var form = new FormData(document.getElementById("tf"));
        $.ajax({
            url: "image/uploadByAlbum",
            type: "post",
            data: form,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data === "FileSuccess") {
                    alert("上传成功")
                } else {
                    alert(data)//这里好像就不会返回页面的内容了，会直接返回字符串
                }
            }
        });
    }

    function upload2() {
        alert("开始上传")
        var form = new FormData(document.getElementById("uploadById"));
        $.ajax({
            url: "image/upload",
            type: "post",
            data: form,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data === "FileSuccess") {
                    alert("上传成功")
                } else {
                    alert(data)//发现有时候SpringMVC会把网页内容返回，看来得对函数作修改啊
                }
            }
        });
    }

    function createFolder() {
        alert("开始创建")
        var form = new FormData(document.getElementById("createFolder"));
        $.ajax({
            url: "folder/CreateFolder",
            type: "post",
            data: form,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data === "success") {
                    alert("创建成功")
                } else {
                    alert(data)//发现有时候SpringMVC会把网页内容返回，看来得对函数作修改啊
                }
            }
        });
    }
</script>
</body>
</html>
