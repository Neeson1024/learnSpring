package com.learn.run;

import com.learn.Utils.ProxyUtils;
import com.learn.app.AppConfig;
import com.learn.handler.MyConstomInvocationHandler;
import com.learn.service.Dao;
import com.learn.service.DaoImpl;
import com.learn.service.InfoService;
import com.learn.service.InfoServiceImpl;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sun.misc.ProxyGenerator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class MyMain {

    public static void main(String[] args) {
        //AnnotationConfigApplicationContext applicationContext
        //        = new AnnotationConfigApplicationContext(AppConfig.class);
        //
        //
        //Dao bean1 = (Dao) applicationContext.getBean(InfoService.class);
        //bean1.ba();
        //
        //InfoService bean = applicationContext.getBean(InfoService.class);
        //bean.test1();
        //bean.test2("hello");
        //System.out.println(bean instanceof InfoService);


        //根据jdk动态生成代理class文件
        //byte[] bytes = ProxyGenerator.generateProxyClass("InfoServiceI", new Class[]{InfoServiceImpl.class});
        //
        //try {
        //    //输出到文件里
        //    FileOutputStream fileOutputStream = new FileOutputStream(new File("d:/InfoServiceI.class"));
        //    fileOutputStream.write(bytes);
        //    fileOutputStream.flush();
        //    fileOutputStream.close();
        //} catch (FileNotFoundException e) {
        //    e.printStackTrace();
        //} catch (IOException e) {
        //    e.printStackTrace();
        //}

        //jdk动态实现
        Dao o = (Dao)Proxy.newProxyInstance(MyMain.class.getClassLoader(), new Class[]{Dao.class}, new MyInvocationHandler(new DaoImpl()));
        try {
            o.ba();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //我们自己实现的动态代理
        Dao proxyDao = (Dao) ProxyUtils.newInstance(Dao.class,new MyConstomInvocationHandler(new DaoImpl()));
        try {
            proxyDao.ba();
            proxyDao.sa("hahahaah");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
