package com.learn.common;

import com.learn.anno.MyAnno;

public class CommonUtils {

    public String getSql(Object object){
        Class<?> aClass = object.getClass();
        if(aClass.isAnnotationPresent(MyAnno.class)){
            MyAnno declaredAnnotation = aClass.getDeclaredAnnotation(MyAnno.class);
            String value = declaredAnnotation.value();
            System.out.println(value);
        }
        return "";
    }

}
