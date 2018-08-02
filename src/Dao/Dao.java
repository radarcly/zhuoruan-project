package Dao;

import java.sql.Connection;
import java.util.ArrayList;

public class Dao {
    public interface DAO<T> {

        public void update(Connection connection, String sql, Object ... args);

        public T get(Connection connection,String sql,Object ... args);

        public ArrayList<T> getForList(Connection connection, String sql, Object ... args);

        public <E> E getForValue(Connection connection,String sql,Object ... args);



    }
}
