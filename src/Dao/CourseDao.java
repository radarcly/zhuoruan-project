package Dao;

import DatabaseTool.JdbcUtils;
import Entity.Course;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class CourseDao {
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();

    //添加课程
    public static void addCourse(String name,int teacherID,String description,String imgPath){
        String sql = "INSERT INTO course (name,description,teacherID,imgPath,startTime) VALUES (?,?,?,?,?)";
        try {
            queryRunner.update(connection,sql,name,description,teacherID,imgPath,new Date(new Date().getTime()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //判断课程是否已经开设
    public static Boolean findWhetherCourseHasExisted(String name,int teacherID){
        String sql = "SELECT * FROM course WHERE name = ? AND teacherID = ?";
        try {
            if(queryRunner.query(connection,sql,new BeanHandler<>(Course.class),name,teacherID)!=null){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //判断用户是否是本课学生
    public static Boolean findWhetherCourseHasChosen(int courseID,int studentID){
        String sql = "SELECT * FROM choose WHERE courseID = ? AND studentID = ?";
        try {
            if(queryRunner.query(connection,sql,new BeanHandler<>(Course.class),courseID,studentID)!=null){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    //判断用户是否是本课老师
    public static Boolean findWhetherIsCourseTeacher(int courseID,int teacherID){
        String sql = "SELECT * FROM course WHERE id = ? AND teacherID = ?";
        try {
            if(queryRunner.query(connection,sql,new BeanHandler<>(Course.class),courseID,teacherID)!=null){
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    //选课
    public static void chooseCourse(int courseID,int studentID){
        String sql = "INSERT INTO choose (courseID,studentID) VALUES (?,?)";
        try {
            queryRunner.update(connection, sql,courseID,studentID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    //退课
    public static void exitCourse(int courseID,int studentID){
        String sql = "delete FROM choose WHERE studentID = ? AND courseID = ? ";
        try {
            queryRunner.update(connection,sql,studentID,courseID);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static int getMaxCourseID(){
        int result=0;
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT * FROM course ORDER BY id DESC limit 0,1";
            result = (int)queryRunner.query(connection, sql, new ScalarHandler());
            System.out.println("result"+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //根据指定sql语句返回对应courselist
    public static ArrayList<Course> getForList(String sql){
        ArrayList<Course> result = new ArrayList<>();
        try {
            result = (ArrayList<Course>) queryRunner.query(connection, sql, new BeanListHandler<>(Course.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
