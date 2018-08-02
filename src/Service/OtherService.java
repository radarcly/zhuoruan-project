package Service;

import DatabaseTool.JdbcUtils;
import Entity.Other;
import Entity.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class OtherService {
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();

    //得到指定课程的所有资源
    public static ArrayList<Other> getOthers(int courseID){
        String sql = "SELECT * FROM other WHERE courseID = ? ";
        try {
            ArrayList<Other> others = (ArrayList<Other>)queryRunner.query(connection,sql,new BeanListHandler<>(Other.class),courseID);
            return others;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Other>();
    }

    //添加资源
    public static void addOther(int courseID,String otherPath){
        String sql = "INSERT INTO other (courseID,otherPath) VALUES (?,?) ";
        try {
           queryRunner.update(connection,sql,courseID,otherPath);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
