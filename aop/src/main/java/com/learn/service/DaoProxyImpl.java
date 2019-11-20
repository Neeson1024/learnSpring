package com.learn.service;

import com.learn.handler.ConstomInvocationHandler;
import com.learn.service.Dao;

import java.lang.reflect.Method;

public class DaoProxyImpl implements Dao {

    private ConstomInvocationHandler h;

    public DaoProxyImpl(ConstomInvocationHandler h){
        this.h = h;
    }

    @Override
    public void ba() throws Exception{
        System.out.println("log");
        Method method = Class.forName("").getMethod("");
        //h.invoke(method);
    }

    public void sa(String str) throws Exception{

    }
}
