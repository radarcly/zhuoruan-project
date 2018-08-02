<html>
<head>
    <title>注册</title>
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="nav.jsp"></c:import>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">注册</h3>
    </div>
    <div class="panel-body">
        <form action="" method="post" id="form">
            <div class="input-group input-group-lg">
                <span class="input-group-addon" >用户名</span>
                <input required type="text" class="form-control" placeholder="必填" aria-describedby="basic-addon1" name="username" value='<%=request.getParameter("username")==null?"":request.getParameter("username")%>'>
            </div>
            <br>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" >密码</span>
                <input required type="password" class="form-control" placeholder="不小于六位且不能为纯数字" aria-describedby="basic-addon1" name="password" value='<%=request.getParameter("password")==null?"":request.getParameter("password")%>'>
            </div>
            <br>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" >重复密码</span>
                <input required type="password" class="form-control" placeholder="应与密码相同" aria-describedby="basic-addon1" name="rpassword" value='<%=request.getParameter("rpassword")==null?"":request.getParameter("rpassword")%>'>
            </div>
            <br>

            <div class="input-group input-group-lg">
                <span class="input-group-addon" >邮箱</span>
                <input required type="email" class="form-control" placeholder="必填" aria-describedby="basic-addon1" name="email" value='<%=request.getParameter("username")==null?"":request.getParameter("email")%>'>
            </div>
            <br>
            <button id="getKey" type="submit" class="btn btn-primary">获取验证码</button>
            <br>
            <br>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" >验证码</span>
                <input required type="text" class="form-control" placeholder="验证码" aria-describedby="basic-addon1" name="key" >
            </div>
            <br>
            <button id="register" type="submit" class="btn btn-primary">注册</button>
            <c:if test="${!empty message}">
                <font color="red">${message}</font>
            </c:if>
        </form>
    </div>
</div>
<script src="js/jquery-3.3.1.js"></script>
<script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="js/nav.js"></script>
<script>
    $(document).ready(function() {
        $("#getKey").click(function () {
            $("form").attr("action", "getKey.user").submit();

        });
        $("#register").click(function () {
            $("form").attr("action", "register.user").submit();

        });
    })

</script>
</body>
</html>
