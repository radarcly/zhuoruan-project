package Dao;

import DatabaseTool.JdbcUtils;
import Entity.Knowledge;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

import static Service.CourseService.getResources;

public class KnowledgeDao {
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();
    public static Knowledge getKnowledgeByID(int knowledgeID){
        String sql = "SELECT * FROM knowledge WHERE id = ?  ";
        try {
            Knowledge knowledge = queryRunner.query(connection,sql,new BeanHandler<>(Knowledge.class),knowledgeID);
            knowledge.setResources(getResources(knowledgeID));
            return knowledge;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
