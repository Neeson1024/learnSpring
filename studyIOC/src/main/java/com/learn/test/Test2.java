package com.learn.test;

import com.learn.service.UserService;
import com.learn.utils.AnnotationConfigApplicationContext;

public class Test2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext();

        annotationConfigApplicationContext.scan("com.learn.*");

        Object userDao = annotationConfigApplicationContext.getBean("userDao");
    }
}
