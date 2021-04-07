package com.chen.myrpc1.provider;

import com.chen.myrpc1.provider.service.impl.TestServiceImpl;

/**
 * @Author: chen
 * @Date: 2021/4/7 5:06 PM
 * @Version 1.0
 */
public class Provider {

    public static void main(String[] args) {
        RpcServer rpcServer = new RpcServer();
        rpcServer.publisher(new TestServiceImpl(),8888);
    }
}
