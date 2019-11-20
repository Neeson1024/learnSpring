package com.learn.test;

import com.learn.dao.UserDaoImpl;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class Test1 {

    public static void main(String[] args) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(UserDaoImpl.class);
        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                if(method.getName().equals("query")){
                    System.out.println("hello word");
                    System.out.println("after");
                }
                return methodProxy.invokeSuper(o,objects);
            }
        });
        UserDaoImpl userDao = (UserDaoImpl) enhancer.create();
        userDao.query();
        System.out.println(userDao.toString());
    }
}
