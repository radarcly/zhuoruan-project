package Dao;

import DatabaseTool.JdbcUtils;
import Entity.Chapter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

public class ChapterDao {
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();

    //得到章节
    public static Chapter getChapterByID(int chapterID){
        Connection connection = JdbcUtils.getConnection();
        String sql = "SELECT * FROM chapter WHERE id = ? ";
        try {
            Chapter chapter =  queryRunner.query(connection,sql,new BeanHandler<>(Chapter.class),chapterID);
            return chapter;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
