package com.learn.service;

import com.learn.anno.MyAnnoTwo;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class InfoServiceImpl implements InfoService {

    public void test1(){
        System.out.println("test1");
    }

    public void test2(String str){
        System.out.println(str);
        System.out.println("test2");
    }
}
