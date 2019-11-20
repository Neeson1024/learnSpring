package com.learn.config;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class aopConfig {

    @Pointcut("execution(* com.learn.dao.UserDaoImpl.*(..))")
    public void pointcutMethod(){

    }

    @Before("pointcutMethod()")
    public void beforeMethod(){
        System.out.println("before");
    }

    @After("pointcutMethod()")
    public void afterMethod(){
        System.out.println("after");
    }

}
