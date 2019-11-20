package com.learn.AopService;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;

import java.lang.reflect.Method;

public class MyAopUtils {

    String path;

    Object adviceObj;

    public MyAopUtils(String path){
        this.path = path;
        if(path == null){
            throw new RuntimeException("please Aop path");
        }
        try {
            Class<?> aClass = Class.forName(path);
            adviceObj = aClass.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

    public MyAopUtils(){}

    public boolean match(Object bean){
        return bean.getClass().getSimpleName().equalsIgnoreCase("userDaoImpl");
    }

    public boolean match(String beanName){
        return true;
    }


    public Object getProxy(Object bean){
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(bean.getClass());
        final Class<?> adviceObjectClass = adviceObj.getClass();

        enhancer.setCallback(new MethodInterceptor() {
            public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
                Method beforeMethod = null;
                Method afterMethod = null;
                for(Method m : adviceObjectClass.getMethods()){
                    if(m.getAnnotation(Before.class) != null){
                        beforeMethod = m;
                    }

                    if(m.getAnnotation(After.class) != null){
                        afterMethod = m;
                    }
                }

                if(beforeMethod != null){
                    beforeMethod.invoke(adviceObj,objects);
                }
                Object resultObj = methodProxy.invokeSuper(o, objects);
                if(afterMethod != null){
                    afterMethod.invoke(adviceObj,objects);
                }

                return resultObj;
            }
        });

        return enhancer.create();
    }
}
