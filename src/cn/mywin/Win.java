package cn.mywin;

import cn.dao.UserDao;
import cn.myjdbc.JDBCUtil;
import cn.myuser.User;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by wyj on 2017/12/14.
 */
public class Win {
    String  sessionid;
    String  message;
    String esessionid;
    //连接本地的 Redis 服务
    Jedis jedis = new Jedis("localhost");
    public  Win(String sessionid,String esessionid, String message){
           this.sessionid=sessionid;
           this.message=message;
           this.esessionid=esessionid;

        if(message.indexOf("&")!=-1){   //判断消息中是否含有&
            System.out.println("包含");
        }
        else
    if(message.contains("!"))
        {
             String[] str=message.split("!");
             message=str[1];

             jedis.set("@"+sessionid,"true");       //增加计时器
            jedis.expire("@"+sessionid, 58);
            jedis.sadd("!"+sessionid, message);//把棋子坐标加入redis表中

            if (jedis.smembers("!"+esessionid).isEmpty()){
                jedis.set("#"+sessionid,"true");     //判断黑白棋
              //  System.out.println("敌方集合是空的");
            }



            //jedis.sadd("!"+esessionid, message);

            System.out.println("不包含");
            System.out.println("测试:"+message);
            System.out.println("获取到的坐标:"+message);



        }


    }
public String cal(String message) {


        boolean isblack=false;
    if(message.substring(0,1).equals("b")){

       //isblack=true;
    }
    if (jedis.exists("#"+sessionid)){
        isblack=true;

    }

    String[] strs=message.split("\\.");
    System.out.println("截取的长度："+strs.length);
    int num1=Integer.parseInt(strs[1]);

    int num2=Integer.parseInt(strs[2]);



    System.out.println("执行了");
    Set<String> set = jedis.smembers("!"+sessionid);
    Iterator<String> it=set.iterator() ;

//初始化棋盘数组

    int chessData[][]=new int[15][15];
    for (int x = 0; x < 15; x++) {

        for (int y = 0; y < 15; y++) {
            chessData[x][y] = 0;
        }

    }
    while(it.hasNext()){
        String obj=it.next();
       String[] str=obj.split(",");

       System.out.println("坐标："+str[0]+str[1]);
        int numa1 = Integer.parseInt(str[0]);
        int numa2= Integer.parseInt(str[1]);

        chessData[numa1][numa2]=1;

    }

    int hz = 0;
    int ve = 0;
    int ls = 0;
    int rs = 0;
      for(int i = num1; i > 0; i--){
          if (chessData[i][num2] != 1) {//判断右边是否有其他的颜色棋子

              break;
          }

          hz++;
      }
    for (int i = num1 + 1; i < 15; i++) {
        if (chessData[i][num2] != 1) {
            break;
        }
        hz++;
    }
    for (int i = num2; i > 0; i--) {      // //计算上下方向的输赢
        if (chessData[num1][i] != 1) {
            break;
        }
        ve++;
    }
    for (int i = num2 + 1; i < 16; i++) {
        if (chessData[num1][i] != 1) {
            break;
        }
        ve++;
    }
    for (int i = num1, j = num2; i > 0&& j > 0; i--, j--) {   //判断左上右下的棋子
        if (chessData[i][j] != 1) {
            break;
        }
        ls++;
    }
    for (int i = num1 + 1, j = num2 + 1; i < 15&& j < 15; i++, j++) {
        if (chessData[i][j] != 1) {
            break;
        }
        ls++;
    }

    for (int i = num1, j = num2; i < 15&&j > 0; i++, j--) {
        if (chessData[i][j] != 1) {
            break;
        }
        rs++;
    }
    for (int i = num1 - 1, j = num2 + 1; i > 0&&j < 15; i--, j++) {
        if (chessData[i][j] != 1) {
            break;
        }
        rs++;
    }



  if (hz==5||ve==5||ls==5||rs==5){


      System.out.println("交集："+jedis.sinter("!"+sessionid,"!"+ esessionid).isEmpty());


          if (isblack) {


             message="@3";
          }else{
              message="@4";
          }

          if (!jedis.sinter("!"+sessionid,"!"+ esessionid).isEmpty()){
              message="@5";   //非法操作

          }
      Set<String> set1 = jedis.smembers("!"+esessionid);
      Iterator<String> it1=set1.iterator() ;
      Set<String> set2 = jedis.smembers("!"+sessionid);
      Iterator<String> it2=set2.iterator() ;
      int cha= set2.size()-set1.size();

      if (isblack) {

          if (cha!=1) {
              message="@5";
          }
      }else{
             if (cha!=0){
                 message="@5";
             }
      }

        int a2=0;
        int a1=0;
      while(it2.hasNext()){
          String obj=it2.next();
          jedis.srem("!"+sessionid, obj);
          a2++;

      }
      while(it1.hasNext()){
          String obj1=it1.next();
          a1++;
          jedis.srem("!"+esessionid, obj1);

      }

      System.out.println("查看sets1集合中的所有元素:"+jedis.smembers("!"+sessionid));
      System.out.println("查看sets2集合中的所有元素:"+jedis.smembers("!"+ esessionid));

  }

    return message;
    }
    public String undo(String message) {
        String[] str=message.split("#");
        message=str[1];
        System.out.println("判断element001是否在集合sets中："+jedis.sismember("!"+sessionid, message));
        if (jedis.sismember("!"+sessionid, message)){
            jedis.srem("!"+sessionid, message);
        }
        if (jedis.sismember("!"+esessionid, message)){
            jedis.srem("!"+esessionid, message);
        }
        return  "good";

    }
    public void  score(String name,boolean iswin){

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

        //将获取的的参数存到uer中，调用操作类中的方法保存


         int id=dao.getid(name);
         int grade= dao.getgrade(id);

         if (iswin){
             grade=grade+1;
         }else{
             grade=grade-1;
         }
         if (id==0){
             return;
         }
         dao.setgrade(id,grade);




    }
public  void record(){
    Set<String> set1 = jedis.smembers("!"+esessionid);

    Iterator<String> it1=set1.iterator() ;
    Set<String> set2 = jedis.smembers("!"+sessionid);
    Iterator<String> it2=set2.iterator();

    while(it2.hasNext()){
        String obj=it2.next();
        jedis.srem("!"+sessionid, obj);
    }
    while(it1.hasNext()){
        String obj1=it1.next();
        jedis.srem("!"+esessionid, obj1);

    }
    if (jedis.exists("#"+sessionid)){
        jedis.del("#"+sessionid);
    }
    if (jedis.exists("#"+esessionid)) {
        jedis.del("#" + esessionid);
    }
    jedis.del(sessionid);
    if (jedis.exists(esessionid)){
        jedis.del(esessionid);
    }

    System.out.println("查看sets1集合中的所有元素:"+jedis.smembers("!"+sessionid));
    System.out.println("查看sets2集合中的所有元素:"+jedis.smembers("!"+ esessionid));
}

}
