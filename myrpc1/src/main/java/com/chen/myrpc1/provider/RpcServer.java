package com.chen.myrpc1.provider;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: chen
 * @Date: 2021/4/7 4:41 PM
 * @Version 1.0
 *  在客户/服务器通信模式中，
 * 服务器端需要创建监听特定端口的ServerSocket，
 * ServerSocket负责接收客户连接请求，
 * 并生成与客户端连接的Socket。
 */
public class RpcServer {

    // 用线程池实现给所有连接都分配一个线程
    private ExecutorService executorService = Executors.newCachedThreadPool();

    //发布服务
    public void publisher(Object service, int port) {

        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(port);
            while (true){
                Socket accept = serverSocket.accept();
                executorService.execute(new ProcessorHandler(accept,service));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭socket
            if (serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
