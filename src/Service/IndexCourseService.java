package Service;

import Dao.CourseDao;
import DatabaseTool.JdbcUtils;
import Entity.Course;
import org.apache.commons.dbutils.ResultSetHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class IndexCourseService {

    // 展示最新的三门课
    public static ArrayList<Course> showNewestCourse(){
        ArrayList<Course> result = new ArrayList<>();
        try {
            String sql = "SELECT * FROM course ORDER BY startTime DESC limit 0,3";
            result = CourseDao.getForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    //展示最热门的三门课
    public static ArrayList<Course> showPopularCourse(){
        ArrayList<Course> result = new ArrayList<>();
        try {
            String sql = "SELECT count(*),courseID,course.name,course.teacherID,course.description,course.imgPath," +
                    "course.id,course.startTime FROM choose,course where choose.courseID = course.id GROUP BY courseID limit 0,3";
            result = CourseDao.getForList(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
