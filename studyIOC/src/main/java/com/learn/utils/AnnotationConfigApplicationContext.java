package com.learn.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnnotationConfigApplicationContext {

    public Map<String,Object> map = new HashMap();

    public void scan(String basePackage){
        List<Class> classList = resolvePath(basePackage);
        for(Class beanClass : classList){

            if(!beanClass.isInterface()) {
                //加载所有bean
                this.doCreateObject(beanClass);
            }
        }
    }

    /**
     * 创建Bean和存放bean 的方法
     * @param beanClazz
     */
    public Object doCreateObject(Class beanClazz){
        Object bean = null;
        if(!beanClazz.isInterface() && beanClazz.isAnnotationPresent(Component.class)) {
            Field[] fields = beanClazz.getDeclaredFields();
            bean = this.parseFieId(beanClazz, fields);
            map.put(beanClazz.getInterfaces()[0].getSimpleName(), bean);
        }
        return bean;
    }


    public Object parseFieId(Class clazz,Field[] fields){
        Object o = null;
        if(fields != null) {
            try {
                //通过构造方法构造对象，这里如果需要传入参数这里会报错
                Constructor constructor = clazz.getConstructor(null);
                o = constructor.newInstance(null);
                for (Field field : fields) {
                    if (field.isAnnotationPresent(Autowired.class)) {
                        Object inObject = map.get(field.getName());
                        if (inObject == null) {
                            inObject = this.doCreateObject(field.getType());
                            if (inObject == null) {
                                throw new RuntimeException(field.getType().getTypeName() + "is not have!!!");
                            }
                        }

                        field.setAccessible(true);
                        field.set(o, inObject);
                    }
                }
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        if(o == null){
            try {
                o = clazz.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return o;
    }

    public Object getBean(String name){
        Object bean = map.get(name);
        if(bean == null){
            throw new RuntimeException("this name bean no have IOC!!!");
        }
        return bean;
    }

    //解析路径
    public List<Class> resolvePath(String basePackage){
        String path = this.getClass().getResource("/").getPath();

        if(basePackage.contains("*")){

            basePackage = basePackage.replace("*","");
        }
        String newBasePackage = basePackage.replaceAll("\\.","\\\\");
        File file = new File(path + "//" + newBasePackage);
        String[] list = file.list();
        List classListAll = new ArrayList();
        try {
            for(String pathStr : list){
                if(!pathStr.contains(".classs")){
                    File file_tmp = new File(path + "//" + newBasePackage + pathStr);
                    String[] list_child = file_tmp.list();
                    for(String path_child_str : list_child){
                        if(path_child_str.contains(".class")){
                            path_child_str = path_child_str.replace(".class", "");

                            Class<?> tmpClass = Class.forName(basePackage + pathStr + "." + path_child_str);
                            if(!tmpClass.isInterface()) {
                                classListAll.add(tmpClass);
                            }
                        }
                    }

                }
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return classListAll;
    }
}
