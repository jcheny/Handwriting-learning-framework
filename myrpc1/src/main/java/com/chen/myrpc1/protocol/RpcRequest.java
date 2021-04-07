package com.chen.myrpc1.protocol;

import java.io.Serializable;

/**
 * @Author: chen
 * @Date: 2021/4/7 4:39 PM
 * @Version 1.0
 */
public class RpcRequest implements Serializable {


    // 类（接口/服务）
    private String className;

    // 方法
    private String methodName;

    // 参数
    private Object[] parameters;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
