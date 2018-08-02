package Servlet;
import Dao.CourseDao;
import Dao.ResourceDao;
import DatabaseTool.JdbcUtils;
import Entity.Course;
import Entity.Homework;
import Entity.Page;
import Entity.User;

import Service.CourseService;
import Service.HomeworkService;
import Service.OtherService;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CourseServlet extends HttpServlet {


    public void service(HttpServletRequest request,HttpServletResponse response) {
        String servletPath = request.getServletPath();
        String methodName = servletPath.substring(1);
        methodName = methodName.substring(0,methodName.length()-7);
        System.out.println(123);
        System.out.println(methodName);
        try {
            Method method = getClass().getDeclaredMethod(methodName,HttpServletRequest.class,HttpServletResponse.class);
            method.invoke(this,request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //开课
    public void addCourse(HttpServletRequest request,HttpServletResponse response){
        System.setProperty("sun.jnu.encoding","utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        System.out.println("here");
        try {
            HashMap<String,String> map = new HashMap<>();
            List<FileItem> items = upload.parseRequest(request);
            for(FileItem item:items){
                if(item.isFormField()){
                    String name = item.getFieldName();
                    String value = item.getString("UTF-8");
                    map.put(name,value);
                }else{
                    QueryRunner queryRunner = new QueryRunner();
                    Connection connection = JdbcUtils.getConnection();
                    String sql = "SELECT * FROM course ORDER BY id DESC limit 0,1";
                    int id = (int)queryRunner.query(connection, sql, new ScalarHandler());
                    System.out.println(id);
                    String imgPath = "img/"+id+".jpg";
                    String name = map.get("name");
                    String description = map.get("description");
                    User user = (User)request.getSession().getAttribute("user");
                    if(name.equals("")||description.equals("")){
                        request.setAttribute("message","课程名或描述信息不能为空");
                        request.getRequestDispatcher("/createCourse.jsp").forward(request,response);
                    }else if (item.getSize()==0){
                        request.setAttribute("message","图片不能为空");
                        request.getRequestDispatcher("/createCourse.jsp").forward(request,response);
                    }else{
                        int teacherID = user.getId();
                        if(CourseService.addCourse(name,teacherID,description,imgPath)==-1){
                            request.setAttribute("message","您已开设此课程无法重新开设");
                            request.getRequestDispatcher("/createCourse.jsp").forward(request,response);
                        }else {
                            String filePath = "D:\\cly\\project\\out\\artifacts\\project_war_exploded\\img\\";
                            String fileName = id + ".jpg";
                            String file = filePath + fileName;
                            File storeFile = new File(file);
                            item.write(storeFile);
                            String filePath2 = "D:\\cly\\project\\web\\img\\";
                            String file2 = filePath2 + fileName;
                            File srcFile = new File(file);
                            File targetFile = new File(file2);
                            InputStream in = new FileInputStream(srcFile);
                            OutputStream out = new FileOutputStream(targetFile);
                            byte[] bytes = new byte[1024];
                            int len = -1;
                            while ((len = in.read(bytes)) != -1) {
                                out.write(bytes, 0, len);
                            }
                            in.close();
                            out.close();
                            request.setAttribute("id", user.getId());
                            request.getRequestDispatcher("/showCourses.course").forward(request, response);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    //选课
    public void chooseCourse(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int studentID = Integer.parseInt(request.getParameter("studentID"));
            if(CourseService.chooseCourse(courseID,studentID)==-1){
                request.setAttribute("message","您已选择此课程无法重新选择");
                request.setAttribute("id",studentID);
                request.getRequestDispatcher("/showCourses.course").forward(request,response);
            }else if(CourseService.chooseCourse(courseID,studentID)==-1){
                request.setAttribute("message","您是此课程老师无法重新选择此课程");
                request.setAttribute("id",studentID);
                request.getRequestDispatcher("/showCourses.course").forward(request,response);
            }else{
                request.setAttribute("id",studentID);
                request.getRequestDispatcher("/showCourses.course").forward(request,response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //展示课程详情
    public void showCourseDetail(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            if(request.getSession().getAttribute("user")==null){
                request.setAttribute("isTeacher",false);
                request.setAttribute("isStudent",false);
                request.setAttribute("isTeacherOrStudent",false);
            }else{
                int teacherID =((User)request.getSession().getAttribute("user")).getId();
                boolean isTeacher = CourseService.isTeacher(courseID,teacherID);
                request.setAttribute("isTeacher",isTeacher);
                request.setAttribute("isStudent", CourseService.isStudent(courseID,teacherID));
                boolean isTeacherOrStudent = CourseService.isTeacherOrStudent(courseID,teacherID);
                request.setAttribute("isTeacherOrStudent",isTeacherOrStudent);
                request.setAttribute("others", OtherService.getOthers(courseID));
            }

            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //展示作业详情
    public void showHomeworkDetail(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int homeworkID = Integer.parseInt(request.getParameter("homeworkID"));

            Course course = CourseService.showCourseDetail(courseID);
            Homework homework = HomeworkService.showHomework(homeworkID);

            int teacherID =((User)request.getSession().getAttribute("user")).getId();
            boolean isTeacher = CourseService.isTeacher(courseID,teacherID);
            request.setAttribute("isTeacher",isTeacher);
            boolean isStudent = CourseService.isStudent(courseID,teacherID);
            request.setAttribute("isStudent", isStudent);
            if(isStudent) {
                for(int i=0;i<homework.getChooseQuestions().size();i++){
                    homework.getChooseQuestions().get(i).setOptionID(HomeworkService.chooseQuestionAnswer(teacherID,homework.getChooseQuestions().get(i).getId()));
                }
                for(int i=0;i<homework.getAnswerQuestions().size();i++){
                    homework.getAnswerQuestions().get(i).setAnswer(HomeworkService.answerQuestionAnswer(teacherID,homework.getAnswerQuestions().get(i).getId()));
                }
            }
            request.setAttribute("course",course);
            request.setAttribute("homework",homework);
            request.getRequestDispatcher("/homework.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查找课程
    public void searchCourse(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            String name,teacher,description;
            if(request.getParameter("name")==null){
                name="";
            }else{
                name = request.getParameter("name");
            }
            if(request.getParameter("teacher")==null){
                teacher="";
            }else{
                teacher = request.getParameter("teacher");
            }

            if(request.getParameter("description")==null){
                description="";
            }else{
                description = request.getParameter("description");
            }
            String currPage = request.getParameter("currentPage");//获取当前页参数
            if (currPage == null || "".equals(currPage.trim())){
                currPage = "1";      // 第一次访问，设置当前页为1;
            }
            int currentPage = Integer.parseInt(currPage);
            Page<Course> page = new Page<Course>();
            page.setCurrentPage(currentPage);
            if(request.getParameter("sort")==null||request.getParameter("sort").equals("hot")){
                CourseService.searchCourseOrderByHot(name,teacher,description,page);
            }else{
                CourseService.searchCourseOrderByTime(name,teacher,description,page);
            }
            request.getSession().setAttribute("page",page);
            request.getRequestDispatcher("/search.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //搜索结果换页
    public void convertPage(HttpServletRequest request,HttpServletResponse response){
        int currPage = Integer.parseInt(request.getParameter("currentPage"));//获取当前页参数
        Page page = (Page)request.getSession().getAttribute("page");
        page.setCurrentPage(currPage);
        if (page.getCurrentPage() <=0) {
            page.setCurrentPage(1);                        // 把当前页设置为1
        } else if (page.getCurrentPage() > page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());        // 把当前页设置为最大页数
        }
        int currentPage = page.getCurrentPage();
        int index = (currentPage -1 ) * page.getPageCount();        // 查询的起始行
        int count = page.getPageCount();

        ArrayList<Course> pagedata;
        if(index+count>page.getAllData().size()){
            pagedata= new ArrayList<Course>(page.getAllData().subList(index,page.getAllData().size()));
        }else{
            pagedata= new ArrayList<Course>(page.getAllData().subList(index,index+count));
        }
        page.setPageData(pagedata);

        request.setAttribute("page", page);
        try {
            request.getRequestDispatcher("search.jsp").forward(request,response);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
    //添加章节
    public void addChapter(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            CourseService.addChapter(name,courseID);
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",true);
            request.setAttribute("isTeacherOrStudent",true);
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加知识点
    public void addKnowledge(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            System.out.println("chapterID:"+request.getParameter("chapterID"));
            int chapterID = Integer.parseInt(request.getParameter("chapterID"));

            CourseService.addKnowledge(name,chapterID);
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",true);
            request.setAttribute("isTeacherOrStudent",true);
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //展示我选的课
    public void showMyChooseCourses(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            ArrayList<Course> courses = CourseService.showMyChooseCourse(id);
            request.setAttribute("courses",courses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //展示我开的课
    public void showMyCreateCourses(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int id = Integer.parseInt(request.getParameter("id"));
            ArrayList<Course> courses = CourseService.showMyCreateCourse(id);
            request.setAttribute("courses",courses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //展示我选的课+我开的课
    public void showCourses(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int id;
            if(request.getParameter("id")!=null){
                id = Integer.parseInt(request.getParameter("id"));
            }else{
                id = Integer.parseInt(request.getAttribute("id").toString());
            }
            ArrayList<Course> myCreateCourses = CourseService.showMyCreateCourse(id);
            request.setAttribute("myCreateCourses",myCreateCourses);
            ArrayList<Course> myChooseCourses = CourseService.showMyChooseCourse(id);
            request.setAttribute("myChooseCourses",myChooseCourses);
            request.getRequestDispatcher("/course.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    } 

    //展示资源
    public void showResource(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int knowledgeID =  Integer.parseInt(request.getParameter("knowledgeID"));
            int chapterID = Integer.parseInt(request.getParameter("chapterID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            if(request.getSession().getAttribute("user")==null){
                request.setAttribute("isTeacher",false);
                request.setAttribute("isTeacherOrStudent",false);
                request.getRequestDispatcher("/detail.jsp").forward(request,response);
            }else{
                request.setAttribute("isTeacher", CourseService.isTeacher(courseID,((User)request.getSession().getAttribute("user")).getId()));
                request.setAttribute("thisChapter", CourseService.getChapterByID(chapterID));
                request.setAttribute("thisKnowledge", CourseService.getKnowledgeByID(knowledgeID));
                request.getRequestDispatcher("/resource.jsp").forward(request,response);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //上传资源
    public void uploadResource(HttpServletRequest request,HttpServletResponse response) throws Exception {
        System.setProperty("sun.jnu.encoding","utf-8");
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        List<FileItem> items = upload.parseRequest(request);
        upload.setHeaderEncoding("UTF-8");
        HashMap<String,String> map = new HashMap<>();
        for(FileItem item:items){

            if(item.isFormField()){
                String name = item.getFieldName();
                String value = item.getString("UTF-8");
                System.out.println(name+"："+value);
                map.put(name,value);
            }else{

                int courseID = Integer.parseInt(map.get("courseID"));
                int knowledgeID = Integer.parseInt(map.get("knowledgeID"));
                int chapterID = Integer.parseInt(map.get("chapterID"));

                int id = ResourceDao.getMaxResourceID()+1;
                String resourcePath = "video/"+id+".mp4";
                CourseService.addResource(knowledgeID,resourcePath);

                String filePath = "D:\\cly\\project\\out\\artifacts\\project_war_exploded\\video\\";
                String fileName = id + ".mp4";
                String file = filePath + fileName;
                File storeFile = new File(file);
                item.write(storeFile);

                String filePath2 = "D:\\cly\\project\\web\\video\\";
                String file2 = filePath2 + fileName;
                File srcFile = new File(file);
                File targetFile = new File(file2);
                InputStream in = new FileInputStream(srcFile);
                OutputStream out = new FileOutputStream(targetFile);
                byte[] bytes = new byte[1024];
                int len = -1;
                while ((len = in.read(bytes)) != -1) {
                    out.write(bytes, 0, len);
                }
                in.close();
                out.close();
                request.setAttribute("isTeacher",true);
                request.setAttribute("thisChapter", CourseService.getChapterByID(chapterID));
                Course course = CourseService.showCourseDetail(courseID);
                request.setAttribute("course",course);
                request.setAttribute("thisKnowledge", CourseService.getKnowledgeByID(knowledgeID));
                request.getRequestDispatcher("/resource.jsp").forward(request,response);
            }

        }
    }

    //退课
    public void exitCourse(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            int studentID = ((User)request.getSession().getAttribute("user")).getId();
            CourseService.exitCourse(studentID,courseID);
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",false);
            request.setAttribute("isStudent",false);
            request.setAttribute("isTeacherOrStudent",false);
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改章节
    public void modifyChapter(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            int chapterID = Integer.parseInt(request.getParameter("chapterID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            CourseService.updateChapter(name,chapterID);
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",true);
            request.setAttribute("isTeacherOrStudent",true);
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除章节
    public void deleteChapter(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int chapterID = Integer.parseInt(request.getParameter("chapterID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            CourseService.deleteChapter(chapterID);
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",true);
            request.setAttribute("isTeacherOrStudent",true);
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改知识点
    public void modifyKnowledge(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            String name = request.getParameter("name");
            int knowledgeID = Integer.parseInt(request.getParameter("knowledgeID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            CourseService.updateKnowledge(name,knowledgeID);
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",true);
            request.setAttribute("isTeacherOrStudent",true);
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除知识点
    public void deleteKnowledge(HttpServletRequest request,HttpServletResponse response){
        try {
            request.setCharacterEncoding("UTF-8");
            int knowledgeID = Integer.parseInt(request.getParameter("knowledgeID"));
            int courseID = Integer.parseInt(request.getParameter("courseID"));
            CourseService.deleteKnowledge(knowledgeID);
            Course course = CourseService.showCourseDetail(courseID);
            request.setAttribute("course",course);
            request.setAttribute("isTeacher",true);
            request.setAttribute("isTeacherOrStudent",true);
            request.getRequestDispatcher("/detail.jsp").forward(request,response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
