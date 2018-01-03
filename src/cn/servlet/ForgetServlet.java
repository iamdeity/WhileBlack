package cn.servlet;

import cn.dao.UserDao;
import cn.mail.JMail;
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

@WebServlet(name = "ForgetServlet")
public class ForgetServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
       // final PrintWriter out = response.getWriter();
        //2.获取输入的用户名和密码
        String email = request.getParameter("email");
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

        User user = new User(email, "");
        // System.out.println(dao.getAllUser(email));
        boolean isexit= dao.getAllUser(email);
        if (!isexit){         //判断是否存在
            response.sendRedirect("/signup.html");
            return;
        }
        System.out.println(email);
        System.out.println("执行了");
        int num = 0;
        JMail mail=new JMail(email);
        try {
            num=  mail.sendemail();

        } catch (Exception e) {
            e.printStackTrace();
        }
        //连接本地的 Redis 服务
        Jedis jedis = new Jedis("localhost");
        //设置 redis 字符串数据

        HttpSession session=request.getSession();
           session.setAttribute("email",email);
           jedis.set("#"+email,""+num);
           jedis.expire("#"+email,300);
           response.sendRedirect("/forget2.html");


      //  System.out.println("连接成功");


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
