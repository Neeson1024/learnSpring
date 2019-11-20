package com.learn.test;

import com.learn.Config.MyConfig;
import com.learn.dao.UserDaoImpl;
import com.learn.dao.UserInfoDao;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.lang.reflect.Method;

public class Test4 {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MyConfig.class);
        UserInfoDao userInfoDao = (UserInfoDao) applicationContext.getBean("userInfoDao");
        userInfoDao.query();

    }
}
