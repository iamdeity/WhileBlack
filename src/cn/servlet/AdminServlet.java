package cn.servlet;

import cn.myjsp.Mysqljsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AdminServlet")
public class AdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //1.设置编码
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        // final PrintWriter out = response.getWriter();
        //2.获取输入的用户名和密码
        String id = request.getParameter("id");
        System.out.println("执行了，www");
        Mysqljsp mysqljsp=new Mysqljsp();
        mysqljsp.deltable(id);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("sadasd");
    }
}
