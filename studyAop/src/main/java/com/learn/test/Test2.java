package com.learn.test;

import com.learn.config.MyConfig;
import com.learn.dao.UserDaoImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test2 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        UserDaoImpl userDao = (UserDaoImpl) applicationContext.getBean("userDaoImpl");

        userDao.query();
    }

}
