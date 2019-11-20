package com.learn.test;

import com.learn.service.UserService;
import com.learn.utils.BeanFactory;

public class Test {

    public static void main(String[] args) {
        BeanFactory beanFactory = new BeanFactory("spring.xml");
        UserService service = (UserService)beanFactory.getBean("service");
        service.find();
    }

}
