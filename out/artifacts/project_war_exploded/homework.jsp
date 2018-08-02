<html>
<head>
    <title>作业</title>
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
        <input type="hidden" id="courseHiddenID" value="${course.id}">
        <input type="hidden" id="homeworkHiddenID" value="${homework.id}">
    </div>
    <div class="panel-body col-sm-6">
        <div class="panel">
            <div class="panel-heading">
                <span>${homework.name}</span>
            </div>
            <div class="panel-body">

                <div class="panel-body">
                    <div class="panel-heading">
                        <h4>简答题列表</h4>
                        <c:if test="${isTeacher}">
                            <button class="btn btn-link pull-right" data-toggle="modal" data-target="#addAnswerQuestionModel">
                                添加简答题
                            </button>
                        </c:if>
                    </div>
                    <div class="panel-body">
                        <ul class="list-group">
                            <c:forEach items="${homework.answerQuestions}" var="question" varStatus="st">
                                <li class="list-group-item">${question.name}
                                    <c:if test="${isTeacher}">
                                        <button class="btn btn-link pull-right" data-toggle="modal" data-target="#modifyAnswerQuestionModel" value="${question.id}">
                                            修改简单题题目
                                        </button>
                                        <button class="btn btn-link pull-right" data-toggle="modal" data-target="#deleteAnswerQuestionModel" value="${question.id}">
                                            删除这道题
                                        </button>
                                    </c:if>
                                    <c:if test="${!isTeacher}">
                                        <c:if test="${question.answer=='-1'}">
                                            <button class="btn btn-link pull-right" data-toggle="modal" data-target="#finishAnswerQuestionModel" value="${question.id}">
                                               完成作业
                                            </button>
                                        </c:if>
                                        <c:if test="${question.answer!='-1'}">
                                            <p>你的答案:${question.answer}</p>
                                        </c:if>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>

                <div class="panel-body">
                    <div class="panel-heading">
                        <h4>单选题列表</h4>
                        <c:if test="${isTeacher}">
                            <button class="btn btn-link pull-right" data-toggle="modal" data-target="#addChooseQuestionModel">
                                添加单选题
                            </button>
                        </c:if>
                    </div>
                    <div class="panel-body">
                        <ul class="list-group">
                            <c:forEach items="${homework.chooseQuestions}" var="question" varStatus="st">
                                <li class="list-group-item" style="height: 80px"><p>${question.name}</p>
                                    <c:if test="${isTeacher}">
                                        <button class="btn btn-link pull-right" data-toggle="modal" data-target="#addOptionModel" value="${question.id}">
                                            添加选项
                                        </button>
                                        <button class="btn btn-link pull-right" data-toggle="modal" data-target="#modifyChooseQuestionModel" value="${question.id}">
                                            修改选择题题目
                                        </button>
                                        <button class="btn btn-link pull-right" data-toggle="modal" data-target="#deleteChooseQuestionModel" value="${question.id}">
                                            删除这道题
                                        </button>
                                    </c:if>
                                    <c:forEach items="${question.options}" var="option" varStatus="st">
                                        <label class="radio-inline">
                                            <c:if test="${option.id==question.optionID}">
                                                <input type="radio" name="${question.id}"  value="${option.id}" checked> ${option.name}
                                            </c:if>
                                            <c:if test="${option.id!=question.optionID}">
                                                <input type="radio" name="${question.id}"  value="${option.id}" > ${option.name}
                                            </c:if>
                                        </label>
                                    </c:forEach>
                                    <c:if test="${!isTeacher}">
                                        <c:if test="${question.optionID==-1}">
                                            <button class="btn btn-link pull-right submitChoose"  value="${question.id}">
                                                提交答案
                                            </button>
                                        </c:if>
                                    </c:if>
                                </li>
                            </c:forEach>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>
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
                    <li class="list-group-item"><a href="showHomeworkDetail.course?homeworkID=${homework.id}&courseID=${course.id}">${homework.name}</a>
                        <c:if test="${isTeacher}">
                            <button class="btn btn-link pull-right" data-toggle="modal" data-target="#modifyHomeworkModel" value="${homework.id}">
                                修改作业名称
                            </button>
                            <button class="btn btn-link pull-right" data-toggle="modal" data-target="#deleteHomeworkModel" value="${homework.id}">
                                删除这个作业
                            </button>
                        </c:if>
                    </li>
                </c:forEach>
            </ul>
        </div>
    </div>
</div>
<%--第一个模态框添加简答题--%>
<form action="addAnswerQuestion.homework" method="post">
    <div class="modal fade" id="addAnswerQuestionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                       添加简答题
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >简答题题目</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID"  value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="添加" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第二个模态框添加单选题--%>
<form action="addChooseQuestion.homework" method="post">
    <div class="modal fade" id="addChooseQuestionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        添加单选题
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >单选题题目</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID"  value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="添加" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第三个模态框添加选项--%>
<form action="addOption.homework" method="post">
    <div class="modal fade" id="addOptionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        添加选项
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >选项</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID" id="courseID" value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="添加" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第四个模态框修改简答题--%>
<form action="modifyAnswerQuestion.homework" method="post">
    <div class="modal fade" id="modifyAnswerQuestionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        修改简答题
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >简答题题目</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID" id="courseID1"  value="${course.id}">
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
<%--第五个模态框修改单选题--%>
<form action="modifyChooseQuestion.homework" method="post">
    <div class="modal fade" id="modifyChooseQuestionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        修改单选题
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >单选题题目</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID" id="courseID2" value="${course.id}">
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
<%--第六个模态框删除简答题--%>
<form action="deleteAnswerQuestion.homework" method="post">
    <div class="modal fade" id="deleteAnswerQuestionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        删除简答题
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID" id="courseID3" value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="删除" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第七个模态框删除单选题--%>
<form action="deleteChooseQuestion.homework" method="post">
    <div class="modal fade" id="deleteChooseQuestionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
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
                <div class="modal-body">
                    <div class="input-group">
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID" id="courseID4" value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="删除" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第八个模态框 添加作业--%>
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
<%--第九个模态框删除作业--%>
<form action="deleteHomework.homework" method="post">
    <div class="modal fade" id="deleteHomeworkModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        删除作业
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <input type="hidden" name="showHomeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID" id="courseID5"  value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="删除" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<%--第十个模态框修改作业--%>
<form action="modifyHomework.homework" method="post">
    <div class="modal fade" id="modifyHomeworkModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                        修改作业名称
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >作业名称</span>
                        <input type="text" class="form-control" name="name"/>
                        <input type="hidden" name="showHomeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID"  id="courseID6" value="${course.id}">
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
<%--第十一个模态框完成作业--%>
<form action="finishAnswerQuestion.homework" method="post">
    <div class="modal fade" id="finishAnswerQuestionModel" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title" >
                       完成作业
                    </h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon" >你的答案</span>
                        <input type="text" class="form-control" name="answer"/>
                        <input type="hidden" name="homeworkID"  value="${homework.id}">
                        <input type="hidden" name="courseID"  id="courseID7" value="${course.id}">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                    </button>
                    <input type="submit" value="提交" class="btn btn-primary">
                </div>
            </div>
        </div>
    </div>
</form>
<script src="js/jquery-3.3.1.js"></script>
<script src="bootstrap/bootstrap-3.3.7-dist/js/bootstrap.min.js" ></script>
<script src="js/nav.js"></script>
<script>
    $('#addOptionModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='questionID'>");
        input.val( e.relatedTarget.value);
        $("#courseID").append(input);
    });
    $('#modifyAnswerQuestionModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='questionID'>");
        input.val( e.relatedTarget.value);
        $("#courseID1").append(input);
    });
    $('#modifyChooseQuestionModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='questionID'>");
        input.val( e.relatedTarget.value);
        $("#courseID2").append(input);
    });
    $('#deleteAnswerQuestionModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='questionID'>");
        input.val( e.relatedTarget.value);
        $("#courseID3").append(input);
    });
    $('#deleteChooseQuestionModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='questionID'>");
        input.val( e.relatedTarget.value);
        $("#courseID4").append(input);
    });
    $('#deleteHomeworkModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='homeworkID'>");
        input.val( e.relatedTarget.value);
        $("#courseID5").append(input);
    });
    $('#modifyHomeworkModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='homeworkID'>");
        input.val( e.relatedTarget.value);
        $("#courseID6").append(input);
    });
    $('#finishAnswerQuestionModel').on('show.bs.modal ', function (e) {
        $("#only").remove();
        sessionStorage.setItem('questionID',e.relatedTarget.value );
        var input=$("<input type='hidden' id='only' name='questionID'>");
        input.val( e.relatedTarget.value);
        $("#courseID7").append(input);
    });
    $('.submitChoose').click(function (e) {
        var questionID = $(this).attr("value");
        console.log("questionID " + questionID);
        var optionID = $("input[name="+questionID+"]:checked").val();
        console.log("optionID " + optionID);
        var homeworkID = $('#homeworkHiddenID').val();
        console.log(homeworkID);
        var courseID = $('#courseHiddenID').val();
        console.log(courseID);
        $.post("finishChooseQuestion.homework",{"homeworkID":homeworkID,"courseID":courseID,"questionID":questionID,"optionID":optionID},function(){
            $("button[value|="+questionID+"]").hide();
        });
    });
</script>
</body>
</html>
