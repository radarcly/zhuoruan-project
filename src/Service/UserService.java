package Service;

import Dao.UserDao;
import DatabaseTool.JdbcUtils;
import Entity.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;

public class UserService {
    //测试完毕 登录 登录成功返回用户id
    public static int login(String username,String password){
        User user = UserDao.get(username,password);
        if(user!=null){
            return user.getId();//登录成功返回用户id
        }else {
            return -1;//-1表示用户名不存在或者密码错误
        }
    }

    //测试完毕 注册
    public static int register(String username,String password){
        if(UserDao.getByName(username)!=null){
            return -1;//用户名已存在返回-1
        }else {
            UserDao.addUser(username, password);
            return login(username, password);//注册成功返回用户id
        }
    }
}
