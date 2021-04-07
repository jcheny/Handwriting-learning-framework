package com.chen.myrpc1.consumer;

import com.chen.myrpc1.provider.service.TestService;

/**
 * @Author: chen
 * @Date: 2021/4/7 5:09 PM
 * @Version 1.0
 */
public class Consumer {

    public static void main(String[] args) {

        // 创建代理对象
        RpcProxyClient rpcProxyClient = new RpcProxyClient();

        // 创建一个代理对象
        TestService service = rpcProxyClient.clientProxy(TestService.class, "localhost", 8888);
        // test() 会进行远程调用，阻塞式...
        String json = service.getName("老五");
        System.out.println(json);
    }

}
