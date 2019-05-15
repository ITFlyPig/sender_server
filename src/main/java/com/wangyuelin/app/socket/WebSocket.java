package com.wangyuelin.app.socket;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint(value = "/webSocket")
public class WebSocket{

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<WebSocket>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    // 监听连接
    @OnOpen
    public void onOpen(Session session) throws IOException {
        this.session = session;
        webSocketSet.add(this);     //加入set中
        try {
            sendMessage("连接成功");
        } catch (IOException e) {
            System.out.println("IO异常");
        }
    }

    // 监听断开连接
    @OnClose
    public void onClose() {
        webSocketSet.remove(this);  //从set中删除
        System.out.println("closed");
    }

    // 监听收到客户端消息
    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println("来自客户端的消息:" + message);

    }

    @OnError
    public void onError(Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    // 发送消息
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    // 模拟群发消息，即服务端统一发送消息给所有已连接的客户端
    public static void sendAll(String message) {
        for (WebSocket item : webSocketSet) {
            try {
                item.sendMessage(message);
            } catch (IOException e) {
            }
        }
    }

}
