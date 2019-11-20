package com.learn.test;

import com.learn.Config.BaDaoImpl;
import com.learn.Config.MyBeanFactoryPostProcessor;
import com.learn.Config.MyConfig;
import com.learn.dao.UserDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test3 {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //添加一个BeanFactoryPostProcessor
        applicationContext.addBeanFactoryPostProcessor(new MyBeanFactoryPostProcessor());
        applicationContext.register(MyConfig.class);
        applicationContext.refresh();
        BaDaoImpl bean = applicationContext.getBean(BaDaoImpl.class);
        BaDaoImpl bean1 = applicationContext.getBean(BaDaoImpl.class);

        System.out.println(bean.hashCode() + "====" + bean1.hashCode());
    }
}
