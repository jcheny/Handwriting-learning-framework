package com.chen.myrpc1.consumer;

import com.chen.myrpc1.protocol.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: chen
 * @Date: 2021/4/7 5:01 PM
 * @Version 1.0
 */
public class RpcNetTransport {

    private String host;
    private int port;

    public RpcNetTransport(String host, int port) {
        this.host = host;
        this.port = port;
    }

    //发送请求获取结果
    public Object send(RpcRequest request) {
        Socket socket = null;
        Object result = null;
        ObjectInputStream inputStream = null;
        ObjectOutputStream outputStream = null;

        try {
            // 将调用的服务的具体信息通过网络写出
            socket = new Socket(host, port);

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            // writeObject实际上是一种序列化
            outputStream.writeObject(request);
            outputStream.flush();

            // 阻塞等待Provider的返回结果
            inputStream = new ObjectInputStream(socket.getInputStream());
            result = inputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

}
