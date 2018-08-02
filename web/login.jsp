<html>
<head>
    <title>登录</title>
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
    <c:import url="nav.jsp"></c:import>
    <div class="panel panel-primary">
        <div class="panel-heading">
            <h3 class="panel-title">登录</h3>
        </div>
        <div class="panel-body">
            <form action="login.user" method="post">
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" >用户名</span>
                    <input  required type="text" class="form-control" placeholder="用户名" aria-describedby="basic-addon1" name="username" value='<%=request.getParameter("username")==null?"":request.getParameter("username")%>'>
                </div>
                <br>
                <div class="input-group input-group-lg">
                    <span class="input-group-addon" >密码</span>
                    <input  required type="password" class="form-control" placeholder="密码" aria-describedby="basic-addon1" name="password" >
                </div>
                <br>
                <input type="submit" value="登录" class="btn btn-primary">
                <c:if test="${!empty message}">
                    <font color="red">${message}</font>
                </c:if>
            </form>
        </div>
    </div>
    <script src="js/jquery-3.3.1.js"></script>
    <script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
    <script src="js/nav.js"></script>
</body>
</html>
