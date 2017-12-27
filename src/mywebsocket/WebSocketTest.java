package mywebsocket;

import cn.configure.GetHttpSessionConfigurator;
import cn.mywin.Win;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint(value = "/websocket", configurator = GetHttpSessionConfigurator.class)
//@ServerEndpoint(value = "/websocket")
public class WebSocketTest {
    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketTest> webSocketSet = new CopyOnWriteArraySet<WebSocketTest>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
     Session session;
    String email=null;
    //连接本地的 Redis 服务
    Jedis jedis = new Jedis("localhost");
    boolean isexit=false;
    int listnumb=0;
    HttpSession httpSession;
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketTest.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketTest.onlineCount--;
    }

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session, EndpointConfig config) {

        this.session = session;
        webSocketSet.add(this);     //加入set中
        addOnlineCount();           //在线数加1

        System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
         httpSession = (HttpSession) config.getUserProperties().get(HttpSession.class.getName());
        //这里上面就是获取到的session对象

        email=""+httpSession.getAttribute("oneuser");
        //存储数据到列表中
         String sessionid=httpSession.getId();   //获取到用户的httpsession的ID
        jedis.set("|"+sessionid,session.getId());
        jedis.set(session.getId(),"|"+sessionid);
     //   System.out.println("session的ID"+sessionid);
       // System.out.println("www"+jedis.get("o"));






        boolean isblack=false;
        if(!jedis.smembers("!"+sessionid).isEmpty()){   //判断是否还有未完成的棋局
            Set<String> set = jedis.smembers("!"+sessionid);
            Iterator<String> it=set.iterator() ;
             String ehttpid= jedis.get(sessionid);

            Set<String> set1 = jedis.smembers("!"+ehttpid);
            Iterator<String> it1=set1.iterator() ;


            if (jedis.exists("#"+sessionid)){
                isblack=true;

            }
            while(it.hasNext()) {

                String obj = it.next();
                if(isblack){
                    try {
                        sendMessage("b"+obj);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        sendMessage("w"+obj);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


            }

            while(it1.hasNext()) {

                String obj1 = it1.next();
                if(isblack){
                    try {
                        sendMessage("w"+obj1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        sendMessage("b"+obj1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }



            }






        }









        if (jedis.get("o")==null){  //判断序列中是否有人在等待
            if (jedis.get(sessionid)==null){
                System.out.println("空的");
                jedis.set("o",sessionid);
            }

        }else{
            if (jedis.get("o").equals(sessionid)){
                System.out.println("你已在等待序列中。。。。。");
            }else{
               // System.out.println(jedis.get("o"));
                System.out.println("配对成功");
                if (jedis.get(sessionid)==null) {
                    jedis.set(sessionid, jedis.get("o"));
                    jedis.set(jedis.get("o"), sessionid);
                    jedis.del("o");

                }
            }

        }




      // jedis.del("list");
       // System.out.println("获取到的session对象：" + httpSession.getAttribute("oneuser"));
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
        jedis.del(session.getId());


    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
       String[] httpidarry= jedis.get(session.getId()).split("\\|");
       String httpid=httpidarry[1];

        String  ehttpid=jedis.get(httpid);
        String esessionid=jedis.get("|"+ehttpid);
        //System.out.println( "ehttpid"+ehttpid);
       // System.out.println(esessionid);
        System.out.println(session + "来自客户端的消息:" + message);
        Win win=new Win(httpid,ehttpid,message);

        if(message.substring(0,1).equals("b")||message.substring(0,1).equals("w")){
             message= win.cal(message);
            //System.out.println("有一方赢了");
        }
        if (message.substring(0,1).equals("#")){
             message=win.undo(message);
        }
        if (message.equals("@6")){
            Set<String> set1 = jedis.smembers("!"+ehttpid);
            Iterator<String> it1=set1.iterator() ;
            Set<String> set2 = jedis.smembers("!"+httpid);
            Iterator<String> it2=set2.iterator() ;


            while(it2.hasNext()){
                String obj=it2.next();
                jedis.srem("!"+httpid, obj);

            }
            while(it1.hasNext()){
                String obj1=it1.next();
                jedis.srem("!"+ehttpid, obj1);

            }
            jedis.del("#"+httpid);
            jedis.del("#"+ehttpid);
            System.out.println("查看sets1集合中的所有元素:"+jedis.smembers("!"+httpid));
            System.out.println("查看sets2集合中的所有元素:"+jedis.smembers("!"+ ehttpid));
        }

       //System.out.println("截取："+message.substring(0,1));

        //群发消息
        for (WebSocketTest item : webSocketSet) {
            try {
               //item.session=session;

                System.out.println(item.session.getId());
                if (item.session.getId().equals(esessionid)){
                    System.out.println("敌人的ip"+item.session.getId());
                    item.sendMessage(message);
                }


            } catch (IOException e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }
}
