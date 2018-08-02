<!DOCTYPE html>
<html lang="en">
<head>
    <script src="jsp/summer/jquery-3.3.1.js"></script>
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap-theme.min.css">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <meta charset="UTF-8">
    <title>首页</title>
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="nav.jsp"></c:import>
<c:if test="${empty newestCourses}">
<%request.getRequestDispatcher("/IndexCourseServlet").forward(request,response);%>
</c:if>



<div id="content">
    <div id="myCarousel" class="carousel slide">
        <!-- 轮播（Carousel）指标 -->
        <ol class="carousel-indicators">
            <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
            <li data-target="#myCarousel" data-slide-to="1"></li>
            <li data-target="#myCarousel" data-slide-to="2"></li>
        </ol>
        <!-- 轮播（Carousel）项目 -->
        <div class="carousel-inner" id="lunbo">
            <c:forEach items="${popularCourses}" var="course" varStatus="st">
                    <div class="item">
                        <a href="showCourseDetail.course?courseID=${course.id}">
                        <h3>${course.name}</h3>
                        <p class="info">${course.description}</p>
                        <img src=${course.imgPath}>
                        </a>
                    </div>
            </c:forEach>
        </div>
        <!-- 轮播（Carousel）导航 -->
        <a class="left carousel-control" href="#myCarousel" role="button" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#myCarousel" role="button" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>

    <!--推荐最新-->
    <div class="CATEGORYS">
        <div class="title">
            <h2>最新/ CATEGORY</h2>
            <div id="showNewCourse">
                <div class="ITEMBOX">
                    <c:forEach items="${newestCourses}" var="course" varStatus="st">
                        <form action="showCourseDetail.course" method="post">
                        <div class="ITEM">
                            <a class="imghover" href="#" target="_blank">
                                <div class="img">
                                    <a href="#"><img style="width: 300px;height: 350px" src=${course.imgPath}></a>
                                </div>
                            </a>
                            <div class="info_box">
                                <div>
                                    <a href="#" target="_blank"><h3>${course.name}</h3></a>
                                    <p class="info">${course.description}</p>
                                    <input type="hidden" name="courseID" value=${course.id}>
                                    <div><button type="submit" class="btcheck" >查看</button></div>
                                </div>
                            </div>
                        </div>
                        </form>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="js/jquery-3.3.1.js"></script>
<script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="js/nav.js"></script>
<script>
    $($("#lunbo").children("div").get(0)).addClass("active");
</script>
</body>
</html>