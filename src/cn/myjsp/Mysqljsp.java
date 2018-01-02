package cn.myjsp;

import cn.dao.UserDao;
import cn.myjdbc.JDBCUtil;

import java.util.ArrayList;
import java.util.List;

public class Mysqljsp {
    List<String> listid=new ArrayList<>();
    List<String> jifen=new ArrayList<>();
    List<String> email=new ArrayList<>();
      public  Mysqljsp(){
          //要连接数据库，先从配置表中获取初始化参数传给JDBSUtil的构造函数

          String url = "jdbc:mysql:///wuziqi";
          String dbuser = "root";
          String dbpass = "hzkjzyjsxy";
          //  System.out.println(url+dbuser+dbpass);
          JDBCUtil util = new JDBCUtil(url, dbuser, dbpass);  //把数据库链接，用户名密码传给jdbcutil，连接数据库

          //userdao--->util--->数据库
          //然后将util作为参数传给操作类

          UserDao dao = new UserDao();
          dao.setUtil(util);
          listid=dao.descid();
          jifen=dao.descgrade();
          for (int i = 0; i < listid.size(); i++) {

              email.add(dao.descemail(listid.get(i)));
             // System.out.println(dao.descemail(listid.get(i)));




          }

      }

    public  List getlist(){

        return  jifen;
    }
    public  List getemail(){

        return  email;
    }
}
