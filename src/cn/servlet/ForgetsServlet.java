package cn.servlet;

import cn.dao.UserDao;
import cn.myjdbc.JDBCUtil;
import cn.myuser.User;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "ForgetsServlet")
public class ForgetsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter out = response.getWriter();

        //2.获取输入的用户名和密码
        String yanzheng = request.getParameter("yanzheng");
        String password = request.getParameter("password");
        String ispassword = request.getParameter("ispassword");


        HttpSession session=request.getSession();
      String email=(String)session.getAttribute("email");
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
          String num= jedis.get("#"+email);

        if (!password.equals(ispassword)){
            response.sendRedirect("/forget2.html");
        }

          if (num.equals(yanzheng)){   //判断验证码是否一一致
              //要连接数据库，先从配置表中获取初始化参数传给JDBSUtil的构造函数
              ServletContext ctx = this.getServletContext();
              String url = ctx.getInitParameter("url");
              String dbuser = ctx.getInitParameter("dbuser");
              String dbpass = ctx.getInitParameter("dbpass");
              //  System.out.println(url+dbuser+dbpass);
              JDBCUtil util = new JDBCUtil(url, dbuser, dbpass);  //把数据库链接，用户名密码传给jdbcutil，连接数据库
              //userdao--->util--->数据库
              //然后将util作为参数传给操作类
              UserDao dao = new UserDao();
              dao.setUtil(util);
              //将获取的的参数存到uer中，调用操作类中的方法保存
              User user = new User(email, password);
              dao.updatepassword(user);
              response.getWriter().write("修改成功！3秒钟跳到主页");
              //设置3秒钟跳转
              response.setHeader("refresh", "3;url=/index.html");

          }else{
              response.sendRedirect("/forget2.html");

          }

        System.out.println("可以："+email);


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
