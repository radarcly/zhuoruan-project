package Service;

import DatabaseTool.JdbcUtils;
import Entity.AnswerQuestion;
import Entity.ChooseQuestion;
import Entity.Homework;
import Entity.Option;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

public class HomeworkService {
    private static QueryRunner queryRunner = new QueryRunner();
    private static Connection connection = JdbcUtils.getConnection();

    public static void main(String[] args) {
//        finisAnswerQuestion(35,1,"是");
//        finishChooseQuestion(35,1,1);
//        System.out.println(chooseQuestionAnswer(35,2));
        System.out.println(answerQuestionAnswer(35,2));
    }

    //添加简答题
    public static void addAnswerQuestion(int homeworkID,String name){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO answerquestion (name,homeworkID) VALUES (?,?)";
            queryRunner.update(connection,sql,name,homeworkID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改简答题
    public static void modifyAnswerQuestion(int answerquestionID,String name){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "UPDATE answerquestion SET name = ? WHERE id = ?";
            queryRunner.update(connection,sql,name,answerquestionID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除简答题
    public static void deleteAnswerQuestion(int answerquestionID){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "DELETE FROM answerquestion WHERE id = ?";
            queryRunner.update(connection,sql,answerquestionID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加选择题
    public static void addChooseQuestion(int homeworkID,String name){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO choosequestion (name,homeworkID) VALUES (?,?)";
            queryRunner.update(connection,sql,name,homeworkID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //修改选择题
    public static void modifyChooseQuestion(int choosequestionID,String name){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "UPDATE choosequestion SET name = ? WHERE id = ?";
            queryRunner.update(connection,sql,name,choosequestionID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //删除选择题
    public static void deleteChooseQuestion(int choosequestionID){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "DELETE FROM choosequestion WHERE id = ?";
            queryRunner.update(connection,sql,choosequestionID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //添加选项
    public static void addChooseQuestionOption(int questionID,String name){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO choosequestionoption (name,questionID) VALUES (?,?)";
            queryRunner.update(connection,sql,name,questionID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 添加作业
    public static void addHomework(String name,int courseID){
        try {
            String sql = "INSERT INTO homework (name,courseID) VALUES (?,?)";
            queryRunner.update(connection,sql,name,courseID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 修改作业
    public static void modifyHomework(String name,int homeworkID){
        try {
            String sql = "UPDATE homework SET name = ? WHERE id = ?";
            queryRunner.update(connection,sql,name,homeworkID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //测试完毕 删除作业
    public static void deleteHomework(int homeworkID){
        try {
            String sql = "DELETE FROM homework WHERE id = ?";
            queryRunner.update(connection,sql,homeworkID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //展示作业
    public static Homework showHomework(int homeworkID){
        Homework homework = new Homework();
        String sql = "SELECT * FROM homework WHERE id = ?";
        try {
            homework = (Homework)queryRunner.query(connection, sql, new BeanHandler(Homework.class),homeworkID);
            //得到简答题
            sql = "SELECT * FROM answerquestion WHERE homeworkID = ?";
            homework.setAnswerQuestions((ArrayList<AnswerQuestion>) queryRunner.query(connection, sql, new BeanListHandler(AnswerQuestion.class),homeworkID));
            //得到选择题
            sql = "SELECT * FROM choosequestion WHERE homeworkID = ?";
            ArrayList<ChooseQuestion> chooseQuestions = (ArrayList<ChooseQuestion>) queryRunner.query(connection, sql, new BeanListHandler(ChooseQuestion.class),homeworkID);
            for(int i=0;i<chooseQuestions.size();i++){
                ChooseQuestion chooseQuestion = chooseQuestions.get(i);
                sql = "SELECT * FROM choosequestionoption WHERE questionID = ?";
                chooseQuestion.setOptions((ArrayList<Option>) queryRunner.query(connection, sql, new BeanListHandler(Option.class),chooseQuestion.getId()));
            }
            homework.setChooseQuestions(chooseQuestions);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return homework;
    }

    //完成单选题
    public static void finishChooseQuestion(int studentID,int questionID,int optionID){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO choosequestionkey (studentID,choosequestionoptionID,choosequestionID) VALUES (?,?,?)";
            queryRunner.update(connection,sql,studentID,optionID,questionID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断学生有没有完成这道单选题，完成返回optionID，没完成返回-1
    public static int chooseQuestionAnswer(int studentID,int questionID){
        int optionID=-2;
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT choosequestionoptionID FROM choosequestionkey WHERE studentID = ? AND choosequestionID = ?";
            if(queryRunner.query(connection,sql,new ScalarHandler(),studentID,questionID)==null){
                return -1;
            }else{
                optionID = (int)queryRunner.query(connection,sql,new ScalarHandler(),studentID,questionID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return optionID;
    }

    //完成简答题
    public static void finisAnswerQuestion(int studentID,int questionID,String answer ){
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "INSERT INTO answerquestionkey (answer,questionID,studentID) VALUES (?,?,?)";
            queryRunner.update(connection,sql,answer,questionID,studentID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //判断学生有没有完成这道简答题，完成返回答案，没完成返回-1
    public static String answerQuestionAnswer(int studentID,int questionID){
        String result="-2";
        try {
            Connection connection = JdbcUtils.getConnection();
            String sql = "SELECT answer FROM answerquestionkey WHERE studentID = ? AND questionID = ?";
            if(queryRunner.query(connection,sql,new ScalarHandler(),studentID,questionID)==null){
                return "-1";
            }else{
                result= (String)queryRunner.query(connection,sql,new ScalarHandler(),studentID,questionID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
