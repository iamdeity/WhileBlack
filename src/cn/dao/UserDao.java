package cn.dao;
import cn.myjdbc.*;
import  cn.myuser.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
    public void savegrade(){

        Connection conn = null;

        PreparedStatement stat = null;

        try {
            String sql = "insert into score values (null,?)";
            conn = util.getConnection();        //创建连接
            stat = conn.prepareStatement(sql);  //创建预处理对象
//            System.out.println("这里执行了");
//            System.out.println(user.getUsername());
//            System.out.println(user.getPass());
            //存储数据（有几个问好，就存几个）
            stat.setString(1, "0");


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


    public boolean getAllUser(String uname){ //判断用户名是否已存在
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
               return true;
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

        return false;
    }

    public User getpassword(String uname){  //判断密码是否正确
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
    public void updatepassword(User user){
        System.out.println("执行了");

        Connection conn = null;

        PreparedStatement stat = null;

        try {
        String  sql  ="update member set pass=? where username=?";
             conn = util.getConnection();
            stat = (PreparedStatement) conn.prepareStatement(sql);

            stat.setString(1, user.getPass());
            stat.setString(2, user.getUsername());

            int count = stat.executeUpdate();
            System.out.println(count+"条数据发生变化");
            stat.close();
            conn.close();
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
    public int getid(String uname){ //获取用户id
        int id = 0;
        
        System.out.println("执行了");
        User user = null;

        Connection conn = null;
        PreparedStatement stat = null;

        ResultSet res = null;

        try {
            String sql = "select id from member where username = ?";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);

            stat.setString(1, uname);

            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            if(res.next()){
               id= res.getInt(1);
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

        return id;
    }
    public int getgrade(int id){ //获取用户积分
        int grade = 0;

        System.out.println("执行了");
        User user = null;

        Connection conn = null;
        PreparedStatement stat = null;

        ResultSet res = null;

        try {
            String sql = "select grade from score where id = ?";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);

            stat.setString(1, ""+id);

            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            if(res.next()){
                grade= res.getInt(1);
                System.out.println("积分为："+grade);
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

        return grade;
    }
    public int setgrade(int id,int grade){ //获取用户积分



        System.out.println("执行了");
        User user = null;

        Connection conn = null;
        PreparedStatement stat = null;

        ResultSet res = null;

        try {
            String sql = "update score set grade=? where id=?";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);

            stat.setString(1, ""+grade);
            stat.setString(2, ""+id);


             stat.executeUpdate();



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

        return grade;
    }
    public List descgrade(){
        List<String> list=new ArrayList<>();

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "SELECT * FROM score ORDER BY grade desc";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);


            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            while (res.next()){
                list.add(res.getString("grade"));
              //  System.out.println();

//                user.setUsername(res.getString("username"));
//                user.setPass(res.getString("pass"));
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
        return  list;


    }
    public List descid(){
        List<String> list=new ArrayList<>();

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "SELECT * FROM score ORDER BY grade desc";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);


            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            while (res.next()){
                list.add(res.getString("id"));
                //  System.out.println();

//                user.setUsername(res.getString("username"));
//                user.setPass(res.getString("pass"));
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
        return  list;


    }
    public List allemail(){
        List<String> list=new ArrayList<>();

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "select username from member ORDER BY id ASC";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);


            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            while (res.next()){
                list.add(res.getString(1));
                //  System.out.println();

//                user.setUsername(res.getString("username"));
//                user.setPass(res.getString("pass"));
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
        return  list;


    }
    public List allid(){
        List<String> list=new ArrayList<>();

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "select id from member ORDER BY id ASC";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);


            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            while (res.next()){
                list.add(res.getString(1));
                //  System.out.println();

//                user.setUsername(res.getString("username"));
//                user.setPass(res.getString("pass"));
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
        return  list;


    }
    public List allgrade(){
        List<String> list=new ArrayList<>();

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "select grade from score ORDER BY id ASC";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);


            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询

            //如果查询到，就将结果集中的数据保存到用户中
            while (res.next()){
                list.add(res.getString(1));
                //  System.out.println();

//                user.setUsername(res.getString("username"));
//                user.setPass(res.getString("pass"));
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
        return  list;


    }
    public String descemail(String id){  //
        String email = null;

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "select username from member where id = ?";
            conn = util.getConnection();
            stat = conn.prepareStatement(sql);

            stat.setString(1, id);

            //把查询到的数据放到结果集中
            res = stat.executeQuery();//查询
            while (res.next()){
                email= res.getString(1);
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

        return email;
    }
    public void delscore(String id){  //

        String email = null;

        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "delete from score where id=?";
            conn = util.getConnection();
           // conn.createStatement();
            stat = conn.prepareStatement(sql);
            stat.setString(1, ""+id);

            //把查询到的数据放到结果集中
            stat.executeUpdate();
            System.out.println("执行了");

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
    public void delmember(String id){  //

       // System.out.println("执行了");
        Connection conn = null;
        PreparedStatement stat = null;
        ResultSet res = null;

        try {
            String sql = "delete from member where id=?";
            conn = util.getConnection();

            stat = conn.prepareStatement(sql);
            stat.setString(1, ""+id);

            //把查询到的数据放到结果集中
            stat.executeUpdate();
            System.out.println("执行了");

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
}
