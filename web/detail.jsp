<html>
<head>
    <title>课程详情</title>
    <link rel="stylesheet" href="bootstrap/bootstrap-3.3.7-dist/css/bootstrap.min.css" >
</head>
<body>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" import="java.util.*" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:import url="nav.jsp"></c:import>
<div class="panel panel-default">
    <div class="panel-heading">
        <h3 class="panel-title">${course.name}</h3>
    </div>
    <div class="panel-body col-sm-6 ">
        <div class="panel">
            <div class="panel-heading">
                <span>章节列表</span>
                <c:if test="${isTeacher}">
                    <button class="btn btn-link pull-right" data-toggle="modal" data-target="#addChapterModel">
                        添加章节
                    </button>
                </c:if>
                <c:if test="${!empty user}">
                    <c:if test="${isTeacherOrStudent==false}">
                        <form action="chooseCourse.course" method="post">
                            <input type="hidden" name="courseID" value=${course.id}>
                            <input type="hidden" name="studentID" value=${user.id}>
                            <input type="submit" value="选课" class="btn btn-link pull-right">
                        </form>
                    </c:if>
                    <c:if test="${isStudent==true}">
                        <form action="exitCourse.course" method="post">
                            <input type="hidden" name="courseID" value=${course.id}>
                            <input type="submit" value="退课" class="btn btn-link pull-right">
                        </form>
                    </c:if>
                </c:if>
            </div>
        </div>
        <div class="panel-body">
            <ul class="list-group">
                <c:forEach items="${course.chapters}" var="chapter" varStatus="st">
                    <li class="list-group-item" >${chapter.name}
                        <c:if test="${isTeacher==true}">
                            <button style="color: blue;" class="btn btn-link pull-right" data-toggle="modal" data-target="#addKnowledgeModel" value="${chapter.id}">
                                添加知识点
                            </button>
                            <button style="color: blue;" class="btn btn-link pull-right" data-toggle="modal" data-target="#deleteChapterModel" value="${chapter.id}">
                                删除该章节
                            </button>
                            <button style="color: blue;" class="btn btn-link pull-right" data-toggle="modal" data-target="#modifyChapterModel" value="${chapter.id}">
                                修改章节名称
                            </button>
                        </c:if>
                    </li>
                    <ul class="list-group">
                        <c:forEach items="${chapter.knowledges}" var="knowledge" varStatus="st">
                            <li class="list-group-item"><a href="showResource.course?knowledgeID=${knowledge.id}&courseID=${course.id}&chapterID=${chapter.id}">${knowledge.name}</a>
                                <c:if test="${isTeacher==true}">
                                    <button style="color: blue;" class="btn btn-link pull-right" data-toggle="modal" data-target="#modifyKnowledgeModel" value="${knowledge.id}">
                                       修改知识点名称
                                    </button>
                                    <button style="color: blue;" class="btn btn-link pull-right" data-toggle="modal" data-target="#deleteKnowledgeModel" value="${knowledge.id}">
                                        删除该知识点
                                    </button>
                                </c:if>
                            </li>
                        </c:forEach>
                    </ul>
                </c:forEach>
            </ul>
        </div>
    </div>
    <c:if test="${isTeacherOrStudent==true}">
        <div class="panel-body col-sm-6">
            <div class="panel-heading">
                <h4>作业列表</h4>
                <c:if test="${isTeacher}">
                    <button class="btn btn-link pull-right" data-toggle="modal" data-target="#addHomeWorkModel">
                        添加作业
                    </button>
                </c:if>
            </div>
            <div class="panel-body">
                <ul class="list-group">
                    <c:forEach items="${course.homeworks}" var="homework" varStatus="st">
                        <li class="list-group-item"><a href="showHomeworkDetail.course?homeworkID=${homework.id}&courseID=${course.id}">${homework.name}</a></li>
                    </c:forEach>
                </ul>
            </div>
            <div class="panel-heading">
                <h4>资源列表</h4>
                <c:if test="${isTeacher}">
                     <form action="upload.other" method="post" enctype="multipart/form-data" style="height:20px">
                        <input type="hidden" name="courseID" value = ${course.id}>
                        <input type="submit" class="btn btn-link pull-right" value="添加资源">
                        <input type="file" value="选择文件" class="btn btn-link pull-right" name="video">
                    </form>
                </c:if>
            </div>
            <div class="panel-body">
                <ul class="list-group">
                    <c:forEach items="${others}" var="other" varStatus="st">
                        <li class="list-group-item"><a href="download.other?name=${other.otherPath}">${other.otherPath}</a></li>
                    </c:forEach>
                </ul>
            </div>
        </div>
    </c:if>

</div>
<%--第一个模态框 添加章节--%>
<form action="addChapter.course" method="post">
<div class="modal fade" id="addChapterModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="myModalLabel">
                    添加新章节
                </h4>
            </div>
            <div class="modal-body">
                <div class="input-group">
                    <span class="input-group-addon" id="basic-addon1">章节名称</span>
                    <input type="text" class="form-control" name="name"/>
                    <input type="hidden" name="courseID" value="${course.id}">
                </div>
            </div>

            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                </button>
                <input type="submit" value="创建" class="btn btn-primary">
            </div>
        </div>
    </div>
</div>
</form>
<%--第二个模态框添加知识点--%>
<form action="addKnowledge.course" method="post">
    <div class="modal fade" id="addKnowledgeModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        添加新知识点
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >知识点名称</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="courseID"  id= "courseID" value="${course.id}">
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="创建" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第三个模态框 添加作业--%>
<form action="addHomework.homework" method="post">
    <div class="modal fade" id="addHomeWorkModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        添加新作业
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >作业名称</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="courseID"  value="${course.id}">
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="创建" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第四个模块框修改章节--%>
<form action="modifyChapter.course" method="post">
    <div class="modal fade" id="modifyChapterModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        修改章节名称
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >章节名称</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="courseID"  id= "courseID2" value="${course.id}">
                    </div>
                </div>

                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="修改" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第五个模块框删除章节--%>
<form action="deleteChapter.course" method="post">
    <div class="modal fade" id="deleteChapterModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        确认删除
                    </h4>
                </div>
                <input type="hidden" name="courseID"  id= "courseID3" value="${course.id}">
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="删除" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第六个模块框修改知识点--%>
<form action="modifyKnowledge.course" method="post">
    <div class="modal fade" id="modifyKnowledgeModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        修改知识点名称
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >知识点名称</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="courseID"  id= "courseID4" value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="修改" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第七个模块框删除知识点--%>
<form action="deleteKnowledge.course" method="post">
    <div class="modal fade" id="deleteKnowledgeModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                       确认删除
                    </h4>
                </div>
                <input type="hidden" name="courseID"  id= "courseID5" value="${course.id}">
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="删除" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>

<script src="js/jquery-3.3.1.js"></script>
<script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="js/nav.js"></script>
<script>
    $('#addKnowledgeModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('chapterID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='chapterID'>");
        input.val( e.relatedTarget.value);
        $("#courseID").append(input);
    });
    $('#modifyChapterModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('chapterID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='chapterID'>");
        input.val( e.relatedTarget.value);
        $("#courseID2").append(input);
    });
    $('#deleteChapterModel').on('show.bs.modal ', function (e){
        $("#only").remove();
        sessionStorage.setItem('chapterID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='chapterID'>");
        input.val( e.relatedTarget.value);
        $("#courseID3").append(input);
    });
    $('#modifyKnowledgeModel').on('show.bs.modal ', function (e){
        $("#only").remove();
        sessionStorage.setItem('chapterID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='knowledgeID'>");
        input.val( e.relatedTarget.value);
        $("#courseID4").append(input);
    });
    $('#deleteKnowledgeModel').on('show.bs.modal ', function (e){
        $("#only").remove();
        sessionStorage.setItem('chapterID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='knowledgeID'>");
        input.val( e.relatedTarget.value);
        $("#courseID5").append(input);
    });
</script>
</body>
</html>
