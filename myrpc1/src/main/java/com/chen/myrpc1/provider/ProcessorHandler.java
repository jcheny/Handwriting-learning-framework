package com.chen.myrpc1.provider;

import com.chen.myrpc1.protocol.RpcRequest;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @Author: chen
 * @Date: 2021/4/7 4:48 PM
 * @Version 1.0
 * 接收（解码）：接收 Client 请求，将二进制流转换成 RpcRequest
 * 执行：获取要执行的方法和参数，调用服务实现对象去执行方法
 * 发送（编码）：将方法执行结果返回，将基本类型/Java对象转换成 二进制流
 */
public class ProcessorHandler implements Runnable {

    private Socket socket;

    private Object server;

    public ProcessorHandler(Socket socket, Object server) {
        this.socket = socket;
        this.server = server;
    }

    @Override
    public void run() {
        try {
            //第一步 获取socket中的流解析成java对象  Object~Stream也是包装类，其作用在于将字节流解析成java对象
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            //将socket中的流读取成请求时候的RpcRequest
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();


            // 第二步：反射调用本地服务
            Object res = invoke(rpcRequest);

            // 第三步：将执行结果返回
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(res);
            objectOutputStream.flush();  // 切记要flush手动刷新

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    // 通过反射具体执行provider提供的方法（即消费者要调用的方法）
    private Object invoke(RpcRequest request) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, ClassNotFoundException {

        // 根据实参获取形参列表
        // 注：获取形参列表后才能确定一个方法
        // 获取所有的参数
        Object[] args = request.getParameters();
        Class<?>[] types = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            types[i] = args[i].getClass();
        }

        // 通过全类名拿到具体具体Class对象
        Class<?> clazz = Class.forName(request.getClassName());

        // 获取 Method
        Method method = clazz.getMethod(request.getMethodName(), types);

        // 执行方法
        // 注：service 这里是单例模式，但是也可以new一个对象后再执行具体方法
        Object res = method.invoke(server, args);

        return res;
    }

}
