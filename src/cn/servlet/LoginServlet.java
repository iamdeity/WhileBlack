package cn.servlet;

import cn.dao.UserDao;
import cn.myjdbc.JDBCUtil;
import cn.myuser.User;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        final PrintWriter out = response.getWriter();
        ////2.获取输入的用户名和密码
        String email = request.getParameter("email");
        String password = request.getParameter("password");
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
        // System.out.println(dao.getAllUser(email));

     //  dao.getgrade(dao.getid(email));

        boolean isexit= dao.getAllUser(email);
        if (isexit){  //判断用户名是否存在
            User getpassword = dao.getpassword(email);
            if (getpassword.getPass().equals(password)){
                request.getSession().setAttribute("oneuser", email);
                response.sendRedirect("/home.jsp");
                out.print("登陆成功");
            }else{
                response.sendRedirect("/index.html");
            }

        }else{
            if (email.equals("admin@qq.com")){
                response.sendRedirect("/admin.jsp");
            }else{
                response.sendRedirect("/index.html");
            }

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
