<html>
<head>
    <title>开课</title>
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="nav.jsp"></c:import>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title" >开课</h3>
    </div>
    <div class="panel-body">
        <form action="addCourse.course" method="post" enctype="multipart/form-data">
            <div class="input-group input-group-lg">
                <span class="input-group-addon" >课程名称</span>
                <input type="text" class="form-control" aria-describedby="basic-addon1" name="name" value='<%=request.getParameter("username")==null?"":request.getParameter("username")%>'>
            </div>
            <br>
            <div class="input-group input-group-lg">
                <span class="input-group-addon" >课程描述</span>
                <input type="text" class="form-control" aria-describedby="basic-addon1" name="description" >
            </div>
            <br>
            <div class="form-group ">
                <label for="exampleInputFile">课程图片</label>
                <input type="file" id="exampleInputFile" name="file">
            </div>
            <br>
            <input type="hidden" name="teacherID" value="${user.id}">
            <input type="submit" value="开课" class="btn btn-primary">
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
