package Dao;

import DatabaseTool.JdbcUtils;
import Entity.Resource;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class ResourceDao {
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();
   //得到指定知识点的所有资源
    public static ArrayList<Resource> getResources(int knowledgeID){
        String sql = "SELECT * FROM resource WHERE knowledgeID = ? ";
        try {
            ArrayList<Resource>  resources = (ArrayList<Resource>)queryRunner.query(connection,sql,new BeanListHandler<>(Resource.class),knowledgeID);
            return resources;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<Resource>();
    }

    //添加资源
    public static void addResource(int knowledgeID,String resourcePath){
        try {
            String sql = "INSERT INTO resource (knowledgeID,resourcePath) VALUES (?,?)";
            queryRunner.update(connection,sql,knowledgeID,resourcePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //得到最大的资源id
    public static int getMaxResourceID(){
        int result=0;
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT * FROM resource ORDER BY id DESC limit 0,1";
            result = (int)queryRunner.query(connection, sql, new ScalarHandler());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
