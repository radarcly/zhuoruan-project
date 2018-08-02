<html>
<head>
    <title>资源</title>
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="nav.jsp"></c:import>
<c:if test="${empty user}">
    <% String site = new String("http://127.0.0.1:8080/login.jsp");
        response.setStatus(response.SC_MOVED_TEMPORARILY);
        response.setHeader("Location", site);%>
</c:if>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">${course.name}</h3>
    </div>
    <div class="panel-body col-sm-6">
        <div class="panel">
            <div class="panel-heading">
                <span>${thisChapter.name}/${thisKnowledge.name}</span>
            </div>
            <div class="panel-body">
                <c:if test="${isTeacher}">
                    <form action="uploadResource.course" method="post" enctype="multipart/form-data">

                        <input type="hidden" name="courseID" value = ${course.id}></video>
                        <input type="hidden" name="chapterID" value = ${thisChapter.id}></video>
                        <input type="hidden" name="knowledgeID" value = ${thisKnowledge.id}></video>
                        <input type="file" value="选择视频" name="video"></video>
                        <input type="submit" value="上传">
                    </form>
                </c:if>
                <c:forEach items="${thisKnowledge.resources}" var="rs" varStatus="st">
                    <video src="${rs.resourcePath}" width="600" height="400" controls="controls">
                        Your browser does not support the video tag.
                    </video>
                </c:forEach>

            </div>
        </div>
    </div>
    <div class="panel-body col-sm-6 ">
        <div class="panel">
            <div class="panel-heading">
                <span>章节列表</span>
            </div>
        </div>
        <div class="panel-body">
            <ul class="list-group">
                <c:forEach items="${course.chapters}" var="chapter" varStatus="st">
                    <li class="list-group-item" style="">${chapter.name}</li>
                    <ul class="list-group">
                        <c:forEach items="${chapter.knowledges}" var="knowledge" varStatus="st">
                            <a class="list-group-item" href="showResource.course?knowledgeID=${knowledge.id}&courseID=${course.id}&chapterID=${chapter.id}">${knowledge.name}</a>
                        </c:forEach>
                    </ul>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<script src="js/jquery-3.3.1.js"></script>
<script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="js/nav.js"></script>
</body>
</html>
