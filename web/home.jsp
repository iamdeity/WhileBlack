<%--
  Created by IntelliJ IDEA.
  User: wyj
  Date: 30/12/2017
  Time: 5:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@   page   import= "cn.myjsp.*"%>
<%
    Mysqljsp mysqljsp=new Mysqljsp();
    int num=(int)((Math.random()*9+1)*100000);


%>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css"
          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

    <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
    <link rel="stylesheet" href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"
          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="https://cdn.bootcss.com/bootstrap/3.3.7/js/bootstrap.min.js"
            integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
            crossorigin="anonymous"></script>
    <style>


        body, html, div, span, ul, li, ol, dl, dd, dt, p, img, h1, h2, h3, h4, h5, h6, a {
            margin: 0;
            padding: 0;
            border: 0;
        }

        h1, h2, h3, h4, h5, h6 {
            font-size: 12px;
            font-weight: normal
        }

        img {
            display: block;
        }

        a {
            text-decoration: none;
        }

        li {
            list-style: none;
        }

        body {
            font-size: 12px;
            color: #666;
            background: #FFF;
        }

    </style>
    <title>五子棋</title>
</head>
<body style="background-color: #f0f0f0 ; text-align: center;">

<!--<div style="width: 1200px ; height: 620px; margin:0 auto;">-->
<div class="container-fluid" style="">
    <div class="row">
        <div class="col-xs-6" >
            <big>风云榜</big>
            <table class="table table-bordered ">
                <tr >
                    <th>名次</th>
                    <th>邮箱</th>
                    <th>积分</th>
                    <th>状态</th>
                    <th>挑战</th>
                </tr>
                <tr>
                    <td>1</td>
                    <td><%=mysqljsp.getemail().get(0)%></td>
                    <td><%=mysqljsp.getlist().get(0)%></td>


                </tr>
                <tr>
                    <td>2</td>
                    <td><%=mysqljsp.getemail().get(1)%></td>
                    <td><%=mysqljsp.getlist().get(1)%></td>


                </tr>
                <tr>
                    <td>3</td>
                    <td><%=mysqljsp.getemail().get(2)%></td>
                    <td><%=mysqljsp.getlist().get(2)%></td>


                </tr>
                <tr>
                    <td>4</td>
                    <td><%=mysqljsp.getemail().get(3)%></td>
                    <td><%=mysqljsp.getlist().get(3)%></td>
                 </tr>
                <tr>
                    <td>5</td>
                    <td><%=mysqljsp.getemail().get(4)%></td>
                    <td><%=mysqljsp.getlist().get(4)%></td>
                </tr>
                <tr>
                    <td>6</td>
                    <td><%=mysqljsp.getemail().get(5)%></td>
                    <td><%=mysqljsp.getlist().get(5)%></td>
                </tr>
                <tr>
                    <td>7</td>
                    <td><%=mysqljsp.getemail().get(6)%></td>
                    <td><%=mysqljsp.getlist().get(6)%></td>
                </tr>
                <tr>
                    <td>8</td>
                    <td><%=mysqljsp.getemail().get(7)%></td>
                    <td><%=mysqljsp.getlist().get(7)%></td>
                </tr>
                <tr>
                    <td>9</td>
                    <td><%=mysqljsp.getemail().get(8)%></td>
                    <td><%=mysqljsp.getlist().get(8)%></td>
                </tr>
                <tr>
                    <td>10</td>
                    <td><%=mysqljsp.getemail().get(9)%></td>
                    <td><%=mysqljsp.getlist().get(9)%></td>
                </tr>

            </table>
        </div>
        <div class="col-xs-6" style=" ">
            <p>
                <a href="one.html"> <button type="button" class="btn btn-primary btn-lg" style="margin-top: 100px">单机模式</button></a>
            </p>
            <p>
                <a href="two.html"> <button type="button" class="btn btn-primary btn-lg" style="margin-top: 100px">人机模式</button></a>
            </p>
            <p>
                <a href="Three.html"> <button type="button" class="btn btn-primary btn-lg" style="margin-top: 100px">联机模式</button></a>
            </p>
            <p>
                <button type="button" class="btn btn-primary btn-lg" style="margin-top: 100px"  onclick="mylink()">生成邀请链接</button>
            </p>
            <div id="myid" style="margin-top: 100px; display:none">http://localhost:8080/friend?name=<%=num%>
            </div>

            <!--style="width: 50%; height: +---100%; background: #ffff00; float: left"-->



        </div>
    </div>
</div>
<script type="text/javascript">
          function mylink() {

              document.getElementById( "myid").style.display= "block"

          }
</script>
</body>
</html>