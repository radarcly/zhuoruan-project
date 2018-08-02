<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"
%>
<link rel="stylesheet" href="css/nav.css" type="text/css">
<nav>
    <div class="logo">
    </div>
    <form class="search_bar" method="post" action="searchCourse.course">
        <div class="search_dropdown" style="width: 16px;">
            <span>课程</span>
            <ul>
                <li class="selected">课程</li>
                <li>老师</li>
                <li>描述</li>
            </ul>
        </div>
        <input type="text" placeholder="Search for anything" />
        <button type="submit" value="Search">Search</button>
    </form>
    <ul>
        <c:if test="${empty user}">
            <li><a href="index.jsp">首页</a></li>
            <li><a href="login.jsp">登录</a></li>
            <li><a href="register.jsp">注册</a></li>
        </c:if>
        <c:if test="${!empty user}">
            <li><a href="index.jsp">首页</a></li>
            <li><a href="showCourses.course?id=${user.id}">Hello ${user.username}</a></li>
            <li><a href="exit.user">退出登录</a><li></li>
        </c:if>
    </ul>
</nav>
<header>
    <div class="headline">
        <div class="inner">
            <h1>Hello</h1>
            <p>Welcome to our course</p>
        </div>
    </div>
</header>





