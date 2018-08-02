package DatabaseTool;

import DatabaseTool.DbcpPool;
import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    public static void releaseConnection(Connection connection) throws SQLException {
        if(connection!=null){
            connection.close();
        }
    }
    public static Connection getConnection(){
        Properties properties = new Properties();
        InputStream inStream = JdbcUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
        try {
            properties.load(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String user = properties.getProperty("user");
        String password = properties.getProperty("password");
        String driverClass = "com.mysql.jdbc.Driver";
        String jdbcUrl = properties.getProperty("jdbcUrl");
        try {
            Class.forName(driverClass);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(jdbcUrl,user,password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
