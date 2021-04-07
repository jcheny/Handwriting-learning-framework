package com.chen.myrpc1.provider.service.impl;

import com.chen.myrpc1.provider.service.TestService;

import java.util.Random;

/**
 * @Author: chen
 * @Date: 2021/4/7 5:04 PM
 * @Version 1.0
 */
public class TestServiceImpl implements TestService {

    @Override
    public String getName(String name) {
        System.out.println("new request coming..." + name);
        Random random = new Random();
        return "{\"name\":" + "\"" + name + "\"" + ", \"age\":" + random.nextInt(40) + "}";
    }
}
