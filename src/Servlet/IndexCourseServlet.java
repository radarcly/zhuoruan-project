package Servlet;

import Entity.Course;
import Service.CourseService;
import Service.IndexCourseService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

public class IndexCourseServlet extends HttpServlet {

    //展示首页的课程
    public void service(HttpServletRequest request, HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            ArrayList<Course> newestCourses = IndexCourseService.showNewestCourse();
            ArrayList<Course> popularCourses = IndexCourseService.showPopularCourse();
            request.setAttribute("newestCourses",newestCourses);
            request.setAttribute("popularCourses",popularCourses);
            request.getRequestDispatcher("/index.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
