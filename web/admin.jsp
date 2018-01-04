<%--
  Created by IntelliJ IDEA.
  User: wyj
  Date: 03/01/2018
  Time: 4:27 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script src="https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js"></script>
<span style="font-size:12px;"><span style="font-size:14px;"><%@ page language="java" import="java.sql.*,java.io.*,java.util.*"%><%@ page
        import="cn.myjsp.Mysqljsp" %>

<html>
<head>
<style type="text/css">
table {
    border: 2px #CCCCCC solid;
    width: 360px;
}

td,th {
    height: 30px;
    border: #CCCCCC 1px solid;
}
</style>
    <title>五子棋</title>
</head>
<body>
    <%
//        //驱动程序名
//        String driverName = "com.mysql.jdbc.Driver";
//        //数据库用户名
//        String userName = "root";
//        //密码
//        String userPasswd = "szy";
//        //数据库名
//        String dbName = "studentmanage";
//        //表名
//        String tableName = "student";
//
//        //联结字符串
//        String url = "jdbc:mysql://localhost:3306/" + dbName + "?user="
//                + userName + "&password=" + userPasswd;
//        Class.forName("com.mysql.jdbc.Driver").newInstance();
//        Connection connection = DriverManager.getConnection(url);
//        Statement statement = connection.createStatement();
//        String sql = "SELECT * FROM " + tableName;
//        ResultSet rs = statement.executeQuery(sql);
        Mysqljsp mysqljsp=new Mysqljsp();
        List<String> email=mysqljsp.getallemail();
        List<String> grade=mysqljsp.getallgrade();
        List<String> id=mysqljsp.getallid();
    %>
    <br>
    <br>
    <table align="center">
        <tr>
            <th>
                <%

                    out.print("id");
                %>
            </th>
            <th>
                <%
                    out.print("email");
                %>
            </th>
            <th>
                <%
                    out.print("积分");
                %>
            </th>
            <th>
                <%
                    out.print("删除");
                %>
            </th>
        </tr>

        <%
           // while (rs.next()) {
            for (int i=0;i<email.size();i++) {
        %>
        <tr>
            <td>
                <%
                    out.print(id.get(i));
                %>
            </td>
            <td>
                <%
                    out.print(email.get(i));
                %>
            </td>
            <td>
                <%
                    out.print(grade.get(i));
                %>
            </td>
            <td>

               <button id="<%=id.get(i)%>" onclick="del(this.id)">删除</button>

            </td>
        </tr>
        <%
            }
        %>
    </table>
    <div align="center">
        <br> <br> <br>

    </div>
    <script type="text/javascript">
        function del(id) {
            //alert("s");
            $.ajax({
                type:"POST",
                url:"/AdminServlet",
                data:{"id":id},
                datatype:"js",
                success:function(data){
//                    $("[name='checkbox2']:checkbox").attr("checked",false);
                    location.reload();//页面刷新
                },
                error:function(data){

                }
            });

        }

    </script>
</body>
</html></span><span style="font-size:24px;color: rgb(255, 0, 0);">
</span></span>
