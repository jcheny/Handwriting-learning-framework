#【RPC】基于 BIO 手写简易 RPC 框架（version1）
RPC(Remote procedure call)远程调用技术，核心就是如何实现远程通信，在架构上类似 C/S（注：Tomcat 是 B/S 架构，底层基于 NIO/Netty。），
即 Provider（Server） 持续提供服务， Consumer（Client） 远程调用服务。目前 Java 提供的网络编程方式有 BIO、NIO、AIO，本篇我们就基于 BIO 来实现一个简易的 RPC 框架。

> PS：RPC 与 HTTP 的关系？RPC 是一种技术的概念名词，HTTP 是一种协议，RPC可以通过 HTTP 来实现，也可以通过Socket自己实现一套协议来实现。

先来看需求：

- Producer

    1.提供一个要暴露的服务（接口）
    
    2.通过框架的 publish() 方法就可以发布一个服务实例
- Consumer：通过框架的 clientProxy() 方法就可以远程调用 Producer 发布的服务，并且拿到返回结果

所以框架需要关注的几个问题：

- Producer 说是发布服务，到底什么叫发布服务？答：类似 C/S 架构，创建一个 SockerServer，持续接收请求
- Consumer 如何发起远程调用？答：可以通过 Socket 发送请求信息，然后将这部分发送逻辑封装到代理对象中
- Producer 与 Consumer 如何通信，即 Producer 如何知道 Consumer 调用什么服务的什么方法？答：自定义协议（RpcRequest）
- Producer 如何处理请求，并返回结果？答：解码 RpcRequest -> 反射执行方法 -> 编码结果
- Consumer 收到响应后如何解析？答：解码，返回给用户
