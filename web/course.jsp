<html>
<head>
    <title>个人页面</title>
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
        <button id="chooseButton" class="btn btn-default">我选的课</button>
        <button id="createButton" class="btn btn-default">我开的课</button>
    </div>
    <div class="panel-body">
        <div class="panel panel-primary" id="choose">
            <div class="panel-heading">
                <h3 class="panel-title">我选的课</h3>
            </div>
            <div class="panel-body">
                <div>
                    <a href="searchCourse.course" class="btn btn-primary" style="margin-bottom: 10px;margin-left: 15px">选课</a>
                </div>
                <c:forEach items="${myChooseCourses}" var="course" varStatus="st">
                    <div class="col-sm-6 col-md-4">
                        <form action="showCourseDetail.course" method="post">
                            <div class="thumbnail">
                                <img style="width: 200px;height: 200px" src=${course.imgPath}>
                                <div class="caption" style="padding-left: 100px">
                                    <h3>${course.name}</h3>
                                    <p>${course.description}</p>
                                    <input type="hidden" name="courseID" value=${course.id}>
                                    <input type="submit" value="查看" class="btn btn-primary">
                                </div>
                            </div>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
        <div class="panel panel-primary" id="create" style="display: none">
            <div class="panel-heading">
                <h3 class="panel-title" >我开的课</h3>
            </div>
            <div class="panel-body">
                <div>
                    <a href="createCourse.jsp" class="btn btn-primary" style="margin-bottom: 10px;margin-left: 15px">开课</a>
                </div>
                <c:forEach items="${myCreateCourses}" var="course" varStatus="st">
                    <div class="col-sm-6 col-md-4">
                        <form action="showCourseDetail.course" method="post">
                            <div class="thumbnail">
                                <img style="width: 200px;height: 200px" src=${course.imgPath}>
                                <div class="caption" style="padding-left: 100px">
                                    <h3>${course.name}</h3>
                                    <p>${course.description}</p>
                                    <input type="hidden" name="courseID" value=${course.id}>
                                    <input type="submit" value="查看" class="btn btn-primary">
                                </div>
                            </div>
                        </form>
                    </div>
                </c:forEach>
            </div>
        </div>
    </div>
</div>


<script src="js/jquery-3.3.1.js"></script>
<script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="js/nav.js"></script>
<script>
    $("#chooseButton").click(function (e) {
      $("#choose").show();
      $("#create").hide();
    });
    $("#createButton").click(function (e) {
        $("#choose").hide();
        $("#create").show();
    })
</script>
</body>
</html>
