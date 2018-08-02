<html>
<head>
    <title>搜索课程</title>
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="nav.jsp"></c:import>
<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">搜索</h3>
    </div>
    <div class="panel-body">
        <form action="searchCourse.course" method="post">
            <div class="input-group">
                <span class="input-group-addon" >课程名称</span>
                <input type="text" class="form-control" aria-describedby="basic-addon1" name="name" value='<%=request.getParameter("name")==null?"":request.getParameter("name")%>'>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon" >课程老师</span>
                <input type="text" class="form-control"  aria-describedby="basic-addon1" name="teacher" value='<%=request.getParameter("teacher")==null?"":request.getParameter("teacher")%>'>
            </div>
            <br>
            <div class="input-group">
                <span class="input-group-addon" >描述</span>
                <input type="text" class="form-control"  aria-describedby="basic-addon1" name="description" value='<%=request.getParameter("description")==null?"":request.getParameter("description")%>' >
            </div>
            <br>
            <div class="input-group" style="margin-bottom: 10px">
                <label class="checkbox-inline">
                    <input type="radio" name="sort" value="hot" checked>按热度排序
                </label>
                <label class="checkbox-inline">
                    <input type="radio" name="sort" value="time"> 按时间排序
                </label>
            </div>
            <input type="submit" value="搜索" class="btn btn-primary">
        </form>
    </div>
</div>

<div class="panel panel-primary">
    <div class="panel-heading">
        <h3 class="panel-title">搜索结果</h3>
    </div>
    <div class="panel-body">
        <c:forEach items="${page.pageData}" var="course" varStatus="st">
            <div class="col-sm-6 col-md-4">
                <div class="thumbnail">
                    <img style="width: 200px;height: 200px" src=${course.imgPath}>
                    <div class="caption" style="padding-left: 100px">
                        <h3>${course.name}</h3>
                        <p>${course.description}</p>
                        <%--<p>${course.studentNum}</p>--%>
                        <c:if test="${empty user}">
                            <form action="showCourseDetail.course" method="post">
                                <input type="hidden" name="courseID" value=${course.id}>
                                <input type="submit" value="查看" class="btn btn-primary">
                            </form>
                        </c:if>
                        <c:if test="${!empty user}">
                            <form action="chooseCourse.course" method="post">
                                <input type="hidden" name="courseID" value=${course.id}>
                                <input type="hidden" name="studentID" value=${user.id}>
                                <input type="submit" value="选课" class="btn btn-primary">
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

<div style="margin-left: 10px;margin-bottom: 10px">
    <tr>
        <td colspan="3" align="center">
            当前${page.currentPage }/${page.totalPage }页     &nbsp;&nbsp;
            <a href="${pageContext.request.contextPath }/convertPage.course?currentPage=1">首页</a>
            <a href="${pageContext.request.contextPath }/convertPage.course?currentPage=${page.currentPage-1}">上一页 </a>
            <a href="${pageContext.request.contextPath }/convertPage.course?currentPage=${page.currentPage+1}">下一页 </a>
            <a href="${pageContext.request.contextPath }/convertPage.course?currentPage=${page.totalPage}">末页</a>
        </td>
    </tr>
</div>



</div>



<script src="js/jquery-3.3.1.js"></script>
<script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="js/nav.js"></script>
</body>
</html>
