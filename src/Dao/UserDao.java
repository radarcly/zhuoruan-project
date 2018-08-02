package Dao;

import DatabaseTool.JdbcUtils;
import Entity.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDao{
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();

    public static void addUser(String username,String password){
        String sql = "INSERT INTO user (username,password) VALUES (?,?)";
        User user = null;
        try {
            queryRunner.update(connection, sql,username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static User get(String username,String password){
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        User user = null;
        try {
            user = queryRunner.query(connection, sql, new BeanHandler<>(User.class),username,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static User getByName(String username){
        String sql = "SELECT * FROM user WHERE username = ? ";
        User user = null;
        try {
            user = queryRunner.query(connection, sql, new BeanHandler<>(User.class),username);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
