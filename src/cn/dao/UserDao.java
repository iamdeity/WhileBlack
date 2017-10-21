package cn.dao;
import cn.myjdbc.*;
import  cn.myuser.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    //因为这个操作类就是对数据库进行操作，所有要连接数据库
    private JDBCUtil util;

    public JDBCUtil getUtil() {
        return util;
    }

    public void setUtil(JDBCUtil util) {
        this.util = util;
    }

    //
    public void saveUser(User user){

        Connection conn = null;

        PreparedStatement stat = null;

        try {
            String sql = "insert into member values (null,?,?,?)";
            conn = util.getConnection();        //创建连接
            stat = conn.prepareStatement(sql);  //创建预处理对象
//            System.out.println("这里执行了");
//            System.out.println(user.getUsername());
//            System.out.println(user.getPass());
            //存储数据（有几个问好，就存几个）
            stat.setString(1, user.getUsername());
            stat.setString(2, user.getPass());
            stat.setString(3, "男");

            stat.executeUpdate();//保存

        } catch (Exception e) {
            e.printStackTrace();
        }finally{

            try {
                if(conn!=null&!conn.isClosed()){
                    conn.close();
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }


    }

    //登录时，通过用户名找到这个对象，因为查询到的是一个对象，所有返回一个user
    public User getUserByName(String uname){
        User user = null;

        Connection conn = null;
        PreparedStatement stat = null;

        ResultSet res = null;

        try {
            String sql = "select * from member where username = ?";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);

            stat.setString(1, uname);

            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            if(res.next()){
                user = new User();
                user.setId(res.getInt("id"));

                user.setUsername(res.getString("username"));
                user.setPass(res.getString("pass"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally{

            try {
                if(conn!=null&!conn.isClosed()){
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return user;
    }
}
