package com.learn.factoryBean;

import com.learn.dao.UserInfoDao;
import org.springframework.beans.factory.FactoryBean;
import sun.misc.ProxyGenerator;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyFactoryBean implements FactoryBean ,InvocationHandler {

    Class clazz;
    public MyFactoryBean(Class clazz){
        this.clazz = clazz;
    }

    public Object getObject() throws Exception {
        Class[] classes = null;
        if(clazz.isInterface()){
            classes = new Class[]{clazz};
        }else{
            classes = clazz.getInterfaces();
        }

        Object o = Proxy.newProxyInstance(this.getClass().getClassLoader(), classes, this);
        return o;
    }

    public Class<?> getObjectType() {
        return clazz;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("aaaa");
        return proxy;
    }
}
