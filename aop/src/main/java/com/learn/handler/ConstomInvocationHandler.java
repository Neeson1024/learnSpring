package com.learn.handler;

import java.lang.reflect.Method;

public interface ConstomInvocationHandler {

    public Object invoke(Method method,Object[] args)throws Exception;
}
