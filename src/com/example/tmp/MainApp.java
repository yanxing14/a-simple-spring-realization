package com.example.tmp;

import java.net.ServerSocket;
import java.net.Socket;

public class MainApp {
    public static void main(String[] args) throws Exception {
        DispatcherServlet servlet=new DispatcherServlet();
        servlet.initialize("com.example.tmp.controller");
        ServerSocket server=new ServerSocket(3333);
        System.out.println("等待客户端连接");
        while(true){
            Socket client=server.accept();
            HttpServer httpServer =new HttpServer();
            httpServer.setClient(client);
            //把servlet返回的jsp传给server
            httpServer.setRespContext(servlet.doGet(httpServer.getPath()));
            httpServer.start();
        }
    }
}
