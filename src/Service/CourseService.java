package Service;
import Dao.ChapterDao;
import Dao.CourseDao;
import Dao.KnowledgeDao;
import Dao.ResourceDao;
import DatabaseTool.JdbcUtils;
import Entity.*;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

public class CourseService {
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();

    public static void main(String[] args) {

    }

    //测试完毕 添加课程，如果已经创建此课程返回-1，成功创建返回0
    public static int addCourse(String name,int teacherID,String description,String imgPath){
        try {
            if(CourseDao.findWhetherCourseHasExisted(name,teacherID)){
                return -1;
            }else{
                CourseDao.addCourse(name,teacherID,description,imgPath);
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -2;
    }

    //测试完毕 选择课程，如果已经选择此课程返回-1，如何是此课程老师返回-2，成功选择返回1
    public static int chooseCourse(int courseID,int studentID){
        int result=-3;
        try {
            if(CourseDao.findWhetherCourseHasChosen(courseID,studentID)){
                return -1;
            }else if(CourseDao.findWhetherIsCourseTeacher(courseID,studentID)){
                return -2;
            }else{
                CourseDao.chooseCourse(courseID,studentID);
                return 1;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -3;
    }

    //退课
    public static void exitCourse(int courseID,int studentID){
        CourseDao.exitCourse(studentID,courseID);
    }

    //测试完毕 展示课程详情（包括章节,知识点,作业）
    public static Course showCourseDetail(int courseID){
        Course course = new Course();
        try {
            Connection connection = JdbcUtils.getConnection();
            //得到课程基本信息
            String sql ="SELECT * FROM course WHERE id = ?";
            ArrayList<Course> temp = (ArrayList<Course>)queryRunner.query(connection,sql,new BeanListHandler<>(Course.class),courseID);
            course = temp.get(0);
            //得到课程作业信息
            sql ="SELECT * FROM homework WHERE courseID = ?";
            ArrayList<Homework> homeworks = (ArrayList<Homework>)queryRunner.query(connection,sql,new BeanListHandler<>(Homework.class),courseID);
            course.setHomeworks(homeworks);



            //得到课程章节信息
            sql ="SELECT * FROM chapter WHERE courseID = ?";
            ArrayList<Chapter> chapters =  (ArrayList<Chapter>)queryRunner.query(connection,sql, new BeanListHandler<>(Chapter.class),courseID);
            for(int i=0;i<chapters.size();i++){
                //得到课程知识点信息
                sql ="SELECT * FROM knowledge WHERE chapterID = ?";
                ArrayList<Knowledge> knowledges = (ArrayList<Knowledge>)queryRunner.query(connection,sql,new BeanListHandler<>(Knowledge.class),chapters.get(i).getId());
                chapters.get(i).setKnowledges(knowledges);
            }
            course.setChapters(chapters);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return course;
    }

    //测试完毕 查找课程 根据热度排序
    public static ArrayList<Course> searchCourseOrderByHot(String name, String teacher, String description, Page page){
        class SortByHot implements Comparator {
            public int compare(Object o1, Object o2) {
                Course s1 = (Course) o1;
                Course s2 = (Course) o2;
                if (s1.getStudentNum() > s2.getStudentNum())
                    return -1;
                return 1;
            }
        }
        ArrayList<Course> result = new ArrayList<>();
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT count(*) FROM user,course WHERE name LIKE ? AND description LIKE ? AND username like ? AND user.id = course.teacherID ";
            Number num = (Number)queryRunner.query(connection,sql,new ScalarHandler(),"%"+name+"%","%"+description+"%","%"+teacher+"%");
            int totalCount = num.intValue();
            page.setTotalCount(totalCount);
            if (page.getCurrentPage() <=0) {
                page.setCurrentPage(1);                        // 把当前页设置为1
            } else if (page.getCurrentPage() > page.getTotalPage()){
                page.setCurrentPage(page.getTotalPage());        // 把当前页设置为最大页数
            }
            int currentPage = page.getCurrentPage();
            int index = (currentPage -1 ) * page.getPageCount();        // 查询的起始行
            int count = page.getPageCount();

            sql = "SELECT * FROM user,course WHERE name LIKE ? AND description LIKE ? AND username like ? AND user.id = course.teacherID";

            result = (ArrayList<Course>)queryRunner.query(connection, sql, new BeanListHandler<>(Course.class),"%"+name+"%","%"+description+"%","%"+teacher+"%");
            for(int i=0;i<result.size();i++){
                sql = "SELECT * FROM course WHERE name = ? AND teacherID = ?";
                Course course = (Course)queryRunner.query(connection,sql,new BeanHandler<>(Course.class),result.get(i).getName(),result.get(i).getTeacherID());
                result.get(i).setId(course.getId());
                sql = "SELECT count(*) FROM choose where courseID = ?";
                num = (Number)queryRunner.query(connection,sql,new ScalarHandler(),result.get(i).getId());
                result.get(i).setStudentNum(num.intValue());
            }
            Collections.sort(result, new SortByHot());
            page.setAllData(result);
            ArrayList<Course> pagedata;
            if(index+count>result.size()){
                pagedata= new ArrayList<Course>(result.subList(index,result.size()));
            }else{
                pagedata= new ArrayList<Course>(result.subList(index,index+count));
            }
            page.setPageData(pagedata);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //查找课程 根据时间排序
    public static ArrayList<Course> searchCourseOrderByTime(String name,String teacher,String description,Page page){
        ArrayList<Course> result = new ArrayList<>();
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT * FROM user,course WHERE name LIKE ? AND description LIKE ? AND username like ? AND user.id = course.teacherID ORDER BY startTime DESC";
            result = (ArrayList<Course>)queryRunner.query(connection, sql, new BeanListHandler<>(Course.class),"%"+name+"%","%"+description+"%","%"+teacher+"%");
            for(int i=0;i<result.size();i++){
                //更改课程id
                sql = "SELECT * FROM course WHERE name = ? AND teacherID = ?";
                Course course = (Course)queryRunner.query(connection,sql,new BeanHandler<>(Course.class),result.get(i).getName(),result.get(i).getTeacherID());
                result.get(i).setId(course.getId());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        page.setTotalCount(result.size());
        if (page.getCurrentPage() <=0) {
            page.setCurrentPage(1);                        // 把当前页设置为1
        } else if (page.getCurrentPage() > page.getTotalPage()){
            page.setCurrentPage(page.getTotalPage());        // 把当前页设置为最大页数
        }
        int currentPage = page.getCurrentPage();
        int index = (currentPage -1 ) * page.getPageCount();        // 查询的起始行
        int count = page.getPageCount();
        page.setAllData(result);
        ArrayList<Course> pagedata;
        if(index+count>result.size()){
            pagedata= new ArrayList<Course>(result.subList(index,result.size()));
        }else{
            pagedata= new ArrayList<Course>(result.subList(index,index+count));
        }
        page.setPageData(pagedata);
        return result;
    }

    //测试完毕 添加章节
    public static void addChapter(String name,int courseID){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO chapter (name,courseID) VALUES (?,?)";
            queryRunner.update(connection,sql,name,courseID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 修改章节
    public static void updateChapter(String name,int chapterID){
        try {
            String sql = "UPDATE chapter SET name = ? WHERE id = ?";
            queryRunner.update(connection,sql,name,chapterID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 删除章节
    public static void deleteChapter(int chapterID){
        try {
            String sql = "DELETE FROM chapter WHERE id = ?";
            queryRunner.update(connection,sql,chapterID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 添加知识点
    public static void addKnowledge(String name,int knowledgeID){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO knowledge (name,chapterID) VALUES (?,?)";
            queryRunner.update(connection,sql,name,knowledgeID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 修改知识点
    public static void updateKnowledge(String name,int knowledgeID){
        try {
            String sql = "UPDATE knowledge SET name = ? WHERE id = ?";
            queryRunner.update(connection,sql,name,knowledgeID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 删除知识点
    public static void deleteKnowledge(int knowledgeID){
        try {
            String sql = "DELETE FROM knowledge WHERE id = ?";
            queryRunner.update(connection,sql,knowledgeID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕  展示我选的课
    public static ArrayList<Course> showMyChooseCourse(int id){
        ArrayList<Course> result = new ArrayList<>();
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT course.id,course.name,course.description,course.imgPath,course.teacherID,course.startTime,choose.courseID,choose.studentID FROM course,choose WHERE studentID = ? AND courseID = course.id";
            result = (ArrayList<Course>) queryRunner.query(connection, sql, new BeanListHandler<>(Course.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //测试完毕 展示我开的课
    public static ArrayList<Course> showMyCreateCourse(int id){
        ArrayList<Course> result = new ArrayList<>();
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT * FROM course WHERE teacherID = ?";
            result = (ArrayList<Course>) queryRunner.query(connection, sql, new BeanListHandler<>(Course.class),id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static int getCourseID(){
        System.out.println("244");
        return CourseDao.getMaxCourseID();
    }

    public static void addResource(int knowledgeID,String resourcePath){
        ResourceDao.addResource(knowledgeID,resourcePath);
    }

    //判断是不是老师
    public static boolean isTeacher(int courseID,int teacherID){
        return CourseDao.findWhetherIsCourseTeacher(courseID,teacherID);
    }

    //判断是不是学生
    public static boolean isStudent(int courseID,int studentID){
       return CourseDao.findWhetherCourseHasChosen(courseID,studentID);
    }

    //判断是不是学生或老师
    public static boolean isTeacherOrStudent(int courseID,int id){
       return isTeacher(courseID,id)||isStudent(courseID, id);
    }

    //得到章节
    public static Chapter getChapterByID(int chapterID){
        return ChapterDao.getChapterByID(chapterID);
    }

    //得到知识点
    public static Knowledge getKnowledgeByID(int knowledgeID){
        return KnowledgeDao.getKnowledgeByID(knowledgeID);
    }

    //得到资源
    public static ArrayList<Resource> getResources(int knowledgeID){
       return ResourceDao.getResources(knowledgeID);
    }



}

