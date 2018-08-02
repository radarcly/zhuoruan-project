package DatabaseTool;

import org.apache.commons.dbcp2.BasicDataSourceFactory;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.Properties;

public class DbcpPool {
    //创建dbcp数据库连接池
    private DataSource dataSource;
    public DbcpPool(){
        Properties properties = new Properties();
        InputStream inputStream = DbcpPool.class.getClassLoader().getResourceAsStream("dbcp.properties");
        try {
            properties.load(inputStream);
            dataSource = BasicDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DataSource getDataSource(){
        return dataSource;
    }
}
