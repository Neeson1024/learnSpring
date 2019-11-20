package com.learn.AopService;

import com.learn.Anno.MyAopCircuit;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyAopBeanPostProcessor implements BeanPostProcessor,BeanFactoryAware {

    MyAopUtils myAopUtils;
    BeanFactory beanFactory;

    public MyAopBeanPostProcessor(String path){
        this.myAopUtils = new MyAopUtils(path);
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        
        if(!myAopUtils.match(bean)){
            return bean;
        }
        System.out.println("ahahaha");
        return myAopUtils.getProxy(bean);
    }

    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
