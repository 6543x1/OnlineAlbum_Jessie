<%--
  Created by IntelliJ IDEA.
  User: 16473
  Date: 2021/2/3
  Time: 14:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片上传</title>
</head>
<body>
<table>
    <tr>
        <td>请选择文件:</td>
        <td><input type="file" accept="image/*" name="file"></td>
    </tr>
    <tr>
        <td><input type="button" value="上传" onclick="test()"></td>
    </tr>
</table>
<script>
    function test() {
        var form = new FormData(document.getElementById("tf"));
        $.ajax({
            url: "user/fileupload1",
            type: "post",
            data: form,
            processData: false,
            contentType: false,
            success: function (data) {
                if (data == "1") {
                    alert("上传成功")
                } else {
                    alert("上传失败")
                }
            }
        });
    }

</script>
</body>
</html>
