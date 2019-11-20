package com.learn.handler;

import java.lang.reflect.Method;

public class MyConstomInvocationHandler implements ConstomInvocationHandler {

    //目标对象
    //如果这里不传目标对象，invoke里的method方法就不能执行
    //invoke(Object obj, Object... args)
    //执行invoke必须要一个目标对象
    private Object target;

    public MyConstomInvocationHandler(Object target){
        this.target = target;
    }

    /**
     *
     * @param method：目标方法
     * @param args：参数
     * @return
     * @throws Exception
     */
    @Override
    public Object invoke(Method method,Object[] args)throws Exception {
        System.out.println("-----------log_-------");
        return method.invoke(target,args);
    }
}
