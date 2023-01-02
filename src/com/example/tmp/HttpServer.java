package com.example.tmp;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.net.Socket;
import java.util.Date;

public class HttpServer extends Thread{
    private Socket client;
    private String respContext;

    //我怕httpServer和DispatcherServlet耦合，所以加了这个，接收dispatcherservlet读出来的jsp文件
    public void setRespContext(String respContext) {
        this.respContext = respContext;
    }

    public void setClient(Socket client) {
        this.client = client;
    }

    //获取路径
    public String getPath() throws IOException {
        InputStream input= client.getInputStream();
        BufferedReader reader=new BufferedReader(new InputStreamReader(input));
        try {
            //第一行一般是Get xxx HTTP/1.1
            String readLine = reader.readLine();
            String[] split = readLine.split(" ");
            if (split.length != 3) {
                return null;
            }
            System.out.println(readLine);
            return split[1];
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void response() throws IOException, ParserConfigurationException, InvocationTargetException, IllegalAccessException, SAXException {
        StringBuffer result = new StringBuffer();
        result.append("HTTP /1.1 200 ok \r\n");
        result.append("Server:张毅琳 \r\n");
        result.append("Content-Type:text/html;charset=utf-8 \r\n");
        result.append((new Date().toString())+"\r\n\n");
        result.append(respContext);
        OutputStream out= client.getOutputStream();
        out.write(result.toString().getBytes("UTF-8"));
        out.flush();
        out.close();
    }

    @Override
    public void run() {
        try {
            response();
        } catch (IOException | ParserConfigurationException | InvocationTargetException | IllegalAccessException |
                 SAXException e) {
            throw new RuntimeException(e);
        }
    }
}
