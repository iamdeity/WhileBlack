package cn.myjdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    private String url;     //url地址
    private String dbuser;  //数据库的用户名
    private String dbpass;  //数据库的登录密码

    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public String getDbuser() {
        return dbuser;
    }
    public void setDbuser(String dbuser) {
        this.dbuser = dbuser;
    }
    public String getDbpass() {
        return dbpass;
    }
    public void setDbpass(String dbpass) {
        this.dbpass = dbpass;
    }

    public JDBCUtil() {
        super();
    }

    public JDBCUtil(String url, String dbuser, String dbpass) {
        super();
        this.url = url;
        this.dbuser = dbuser;
        this.dbpass = dbpass;

    }

    //静态代码块，加载驱动
    static{
        try {
            Class.forName("com.mysql.jdbc.Driver");
            System.out.println("1");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //对外提供一个连接数据库的方法
    public Connection getConnection() throws Exception{
        return DriverManager.getConnection(this.url, this.dbuser, this.dbpass);
    }
}
