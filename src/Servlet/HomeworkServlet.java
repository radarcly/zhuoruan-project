package Servlet;

import Entity.Course;
import Entity.Homework;
import Entity.User;
import Service.CourseService;
import Service.HomeworkService;
import Service.OtherService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class HomeworkServlet extends HttpServlet {

    public void service(HttpServletRequest request,HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0,methodName.length()-9);       
        try {
            Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加作业
    public void addHomework(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            HomeworkService.addHomework(name,courseID);
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",true);
            request.setAttribute("isTeacherOrStudent",true);
            request.setAttribute("others", OtherService.getOthers(courseID));
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改作业
    public void modifyHomework(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int showHomeworkID = Integer.parseInt(request.getParameter("showHomeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));           ;
            String name = request.getParameter("name");
            HomeworkService.modifyHomework(name,homeworkID);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(showHomeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除作业
    public void deleteHomework(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int showHomeworkID = Integer.parseInt(request.getParameter("showHomeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            HomeworkService.deleteHomework(homeworkID);
            Course course = CourseService.showCourseDetail(courseID);
            Homework homework;
            if(homeworkID==showHomeworkID){
                homework = HomeworkService.showHomework(1);
            }else{
                homework = HomeworkService.showHomework(showHomeworkID);
            }
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加简答题
    public void addAnswerQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            String name = request.getParameter("name");
            HomeworkService.addAnswerQuestion(homeworkID,name);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改简答题
    public void modifyAnswerQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int questionID = Integer.parseInt(request.getParameter("questionID"));
            String name = request.getParameter("name");
            HomeworkService.modifyAnswerQuestion(questionID,name);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除简答题
    public void deleteAnswerQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int questionID = Integer.parseInt(request.getParameter("questionID"));
            String name = request.getParameter("name");
            HomeworkService.deleteAnswerQuestion(questionID);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加选择题
    public void addChooseQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            String name = request.getParameter("name");
            HomeworkService.addChooseQuestion(homeworkID,name);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改选择题
    public void modifyChooseQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int questionID = Integer.parseInt(request.getParameter("questionID"));
            String name = request.getParameter("name");
            HomeworkService.modifyChooseQuestion(questionID,name);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除选择题
    public void deleteChooseQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int questionID = Integer.parseInt(request.getParameter("questionID"));
            HomeworkService.deleteChooseQuestion(questionID);
            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加选项
    public void addOption(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int questionID = Integer.parseInt(request.getParameter("questionID"));
            String name = request.getParameter("name");
            HomeworkService.addChooseQuestionOption(questionID,name);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",true);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //完成简答题
    public void finishAnswerQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int questionID = Integer.parseInt(request.getParameter("questionID"));
            int studentID =((User)request.getSession().getAttribute("user")).getId();
            String answer = request.getParameter("answer");
            HomeworkService.finisAnswerQuestion(studentID,questionID,answer);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            for(int i=0;i<homework.getChooseQuestions().size();i++){
                homework.getChooseQuestions().get(i).setOptionID(HomeworkService.chooseQuestionAnswer(studentID,homework.getChooseQuestions().get(i).getId()));
            }
            for(int i=0;i<homework.getAnswerQuestions().size();i++){
                homework.getAnswerQuestions().get(i).setAnswer(HomeworkService.answerQuestionAnswer(studentID,homework.getAnswerQuestions().get(i).getId()));
            }
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",false);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //完成单选题
    public void finishChooseQuestion(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int questionID = Integer.parseInt(request.getParameter("questionID"));
            int studentID =((User)request.getSession().getAttribute("user")).getId();
            int optionID = Integer.parseInt(request.getParameter("optionID"));
            HomeworkService.finishChooseQuestion(studentID,questionID,optionID);

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);
            for(int i=0;i<homework.getChooseQuestions().size();i++){
                homework.getChooseQuestions().get(i).setOptionID(HomeworkService.chooseQuestionAnswer(studentID,homework.getChooseQuestions().get(i).getId()));
            }
            for(int i=0;i<homework.getAnswerQuestions().size();i++){
                homework.getAnswerQuestions().get(i).setAnswer(HomeworkService.answerQuestionAnswer(studentID,homework.getAnswerQuestions().get(i).getId()));
            }
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.setAttribute("isTeacher",false);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
