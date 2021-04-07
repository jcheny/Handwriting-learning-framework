package com.chen.myrpc1.consumer;

import com.chen.myrpc1.protocol.RpcRequest;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Author: chen
 * @Date: 2021/4/7 5:00 PM
 * @Version 1.0
 */
public class RemoteInvocationHandler implements InvocationHandler {

    private String host;
    private int port;

    RemoteInvocationHandler(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 构建调用Provider的请求参数
        RpcRequest rpcRequest = new RpcRequest();
        rpcRequest.setClassName(method.getDeclaringClass().getName());
        rpcRequest.setMethodName(method.getName());
        rpcRequest.setParameters(args);

        // 进行远程调用，并返回执行结果
        RpcNetTransport netTransport = new RpcNetTransport(host, port);
        Object res = netTransport.send(rpcRequest);

        return res;

    }
}
